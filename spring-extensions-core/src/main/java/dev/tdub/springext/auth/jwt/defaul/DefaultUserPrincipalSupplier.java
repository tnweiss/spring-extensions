package dev.tdub.springext.auth.jwt.defaul;

import dev.tdub.springext.auth.UserPrincipal;

import java.util.function.Supplier;

public interface DefaultUserPrincipalSupplier extends Supplier<UserPrincipal> {
}
