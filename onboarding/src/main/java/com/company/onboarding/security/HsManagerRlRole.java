package com.company.onboarding.security;

import com.company.onboarding.entity.Comission;
import com.company.onboarding.entity.User;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "HS manager’s comission and users", code = "hs-manager-rl")
public interface HsManagerRlRole {
    @JpqlRowLevelPolicy(
            entityClass = Comission.class,
            where = "{E}.hsManager.id = :current_user_id")
    void department();

    @JpqlRowLevelPolicy(
            entityClass = User.class,
            where = "{E}.comission.hsManager.id = :current_user_id")
    void user();
}