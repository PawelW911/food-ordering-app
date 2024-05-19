package org.app.util;

import org.app.domain.Role;

import java.util.Set;

public class RolesFixtures {
    public static Set<Role> someRolesForOwner() {
        return Set.of(Role.builder()
                        .id(1)
                        .role("OWNER")
                .build());
    }

    public static Set<Role> someRolesForCustomer() {
        return Set.of(Role.builder()
                        .id(2)
                        .role("CUSTOMER")
                .build());
    }
}
