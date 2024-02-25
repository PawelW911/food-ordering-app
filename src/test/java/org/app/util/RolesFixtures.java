package org.app.util;

import org.app.domain.Role;
import org.app.security.RoleEntity;

import java.util.Set;

public class RolesFixtures {
    public static Set<Role> someRolesForOwner() {
        return Set.of(Role.builder()
                        .role("OWNER")
                .build());
    }

    public static Set<Role> someRolesForCustomer() {
        return Set.of(Role.builder()
                        .role("CUSTOMER")
                .build());
    }
}
