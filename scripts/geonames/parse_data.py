import os
import requests
from io import BytesIO
import zipfile

from typing import List, Dict, TypedDict

# https://public.opendatasoft.com/explore/dataset/countries-codes/export/?flg=en-us
COUNTRY_CODES_NAMES = ('https://public.opendatasoft.com/api/explore/v2.1/catalog/datasets/countries-codes/exports/csv'
                       '?lang=en&timezone=America%2FNew_York&use_labels=true&delimiter=%3B')

# extract data from zip file
# https://download.geonames.org/export/dump/
DATA_URLS = [
    'https://download.geonames.org/export/zip/US.zip'
]

# get current files directory
CURRENT_DIR = os.path.dirname(os.path.realpath(__file__))
DATA_FOLDER = os.path.join(CURRENT_DIR, '..', '..', 'spring-extensions-core', 'src', 'main', 'resources')


# define a Country typed dict
class Iso3166Country(TypedDict):
    name: str
    iso_code: str


class Iso3166Subdivision(TypedDict):
    name: str
    iso_code: str


class GeoNameEntry(TypedDict):
    country_iso_code: str
    subdivision_name: str
    subdivision_iso_code: str
    postal_code: str


def get_country_names() -> Dict[str, Iso3166Country]:
    """
    Get country names from the country codes file
    :return: Dict of country names
    """
    response = requests.get(COUNTRY_CODES_NAMES)

    # read content as tsv
    content = response.content.decode('utf-8').split('\n')

    # extract data
    country_names: Dict[str, Iso3166Country] = {}
    for line in content[1:-1]:
        line = line.split(';')

        # extract data
        country_iso_code = line[1]
        country_name = line[6]

        # append to data
        country_names[country_iso_code] = {
            'name': country_name,
            'iso_code': country_iso_code
        }

    return country_names


def fetch_geonames_data() -> List[GeoNameEntry]:
    data: List[GeoNameEntry] = []

    for data_url in DATA_URLS:
        response = requests.get(data_url)

        zip_data = BytesIO(response.content)

        with zipfile.ZipFile(zip_data) as zip_file:
            content = zip_file.open('US.txt')

            # read content as tsv
            for line in content.readlines():
                line = line.decode('utf-8')
                line = line.split('\t')

                if line[3] and line[0] and line[4] and line[1]:
                    data.append({
                        'country_iso_code': line[0],
                        'subdivision_name': line[3],
                        'subdivision_iso_code': line[4],
                        'postal_code': line[1]
                    })
    return data


def write_countries(_data_folder: str, _countries: Dict[str, Iso3166Country]):
    data_file = os.path.join(_data_folder, 'countries.tsv')
    with open(data_file, 'w', encoding='utf-16') as f:
        output = '\n'.join([f'{country["iso_code"]}\t{country["name"]}' for country in _countries.values()])
        f.write(output)


def write_subdivisions(_data_folder: str, _geonames_data: List[GeoNameEntry]):
    data_file = os.path.join(_data_folder, 'subdivisions.tsv')

    unique_subdivisions: Dict[str, str] = {}
    for country in _geonames_data:
        unique_subdivisions[f'{country["country_iso_code"]}-{country["subdivision_iso_code"]}'] = \
            country['subdivision_name']

    with open(data_file, 'w', encoding='utf-16') as f:
        output = '\n'.join([f'{k}\t{v}' for k, v in unique_subdivisions.items() if k and v])
        f.write(output)


def write_postal_codes(_data_folder: str, _geonames_data: List[GeoNameEntry]):
    data_file = os.path.join(_data_folder, 'postal-codes.tsv')

    with open(data_file, 'w', encoding='utf-16') as f:
        output = '\n'.join([
            f'{record["country_iso_code"]}\t{record["country_iso_code"]}-{record["subdivision_iso_code"]}\t{record["postal_code"]}'
            for record in _geonames_data])
        f.write(output)


if __name__ == '__main__':
    countries: Dict[str, Iso3166Country] = get_country_names()
    geonames_data: List[GeoNameEntry] = fetch_geonames_data()

    write_countries(DATA_FOLDER, countries)
    write_subdivisions(DATA_FOLDER, geonames_data)
    write_postal_codes(DATA_FOLDER, geonames_data)
