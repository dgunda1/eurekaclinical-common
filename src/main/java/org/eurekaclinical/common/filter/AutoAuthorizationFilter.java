package org.eurekaclinical.common.filter;

import javax.inject.Inject;

/*-
 * #%L
 * Eureka! Clinical Common
 * %%
 * Copyright (C) 2016 - 2018 Emory University
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import javax.inject.Singleton;
import org.eurekaclinical.standardapis.dao.UserDao;
import org.eurekaclinical.standardapis.dao.UserTemplateDao;
import org.eurekaclinical.standardapis.entity.RoleEntity;
import org.eurekaclinical.standardapis.entity.UserEntity;
import org.eurekaclinical.standardapis.entity.UserTemplateEntity;

/**
 *
 * @author Andrew Post
 * @param <R>
 * @param <U>
 * @param <T>
 */
@Singleton
public class AutoAuthorizationFilter<R extends RoleEntity, U extends UserEntity<R>, T extends UserTemplateEntity<R>> extends AbstractAutoAuthorizationFilter {
    private final UserTemplateDao<? extends RoleEntity,? extends UserTemplateEntity<? extends RoleEntity>> userTemplateDao; 
    
    @Inject
    public AutoAuthorizationFilter(UserTemplateDao<? extends RoleEntity,? extends UserTemplateEntity<? extends RoleEntity>> inUserTemplateDao,
            UserDao<? extends UserEntity<? extends RoleEntity>> inUserDao) {
        super(inUserTemplateDao, inUserDao);
        this.userTemplateDao = inUserTemplateDao;
    }
 
	@Override
    protected U toUserEntity(UserTemplateEntity userTemplate, String username) {
        return (U) this.userTemplateDao.newUserEntityInstance(username, userTemplate.getRoles());
    }
    
}
