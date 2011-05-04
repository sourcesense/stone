/*
 *  Copyright 2011 Sourcesense
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
*/
package com.sourcesense.stone.jaas.modeshape;

import java.security.Principal;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class StoneLoginModule implements LoginModule {

	private Subject subject;

	private Map<String, ?> sharedState;

	@Override
	public boolean abort() throws LoginException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean commit() throws LoginException {
		// TODO Auto-generated method stub
		subject.getPrincipals().add(new Principal() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return (String) sharedState.get("name");
			}

		});
		subject.getPrincipals().add(new Group() {

			Principal admin = new Principal() {

				@Override
				public String getName() {
					// TODO Auto-generated method stub
					return "admin";
				}
			};

			List<Principal> members = new ArrayList<Principal>();

			{
				members.add(admin);
			}

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "stone_groups";
			}

			@Override
			public boolean addMember(Principal user) {
				// TODO Auto-generated method stub
				return members.add(user);
			}

			@Override
			public boolean isMember(Principal member) {
				// TODO Auto-generated method stub
				return members.contains(member);
			}

			@Override
			public Enumeration<? extends Principal> members() {
				// TODO Auto-generated method stub
				return Collections.enumeration(members);
			}

			@Override
			public boolean removeMember(Principal user) {
				// TODO Auto-generated method stub
				return members.remove(user);
			}

		});
		return true;
	}

	@Override
	public void initialize(Subject arg0, CallbackHandler arg1,
			Map<String, ?> arg2, Map<String, ?> arg3) {
		// TODO Auto-generated method stub
		subject = arg0;
		sharedState = arg2;
		NameCallback nameCallback = new NameCallback("name");
		PasswordCallback passwordCallback = new PasswordCallback("password",
				true);
		Callback[] callbacks = new Callback[] { nameCallback, passwordCallback };
		try {
			arg1.handle(callbacks);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		((Map<String, String>) sharedState).put("name", nameCallback.getName());
		((Map<String, String>) sharedState).put("password", new String(
				passwordCallback.getPassword()));
	}

	@Override
	public boolean login() throws LoginException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		// TODO Auto-generated method stub
		return true;
	}

}
