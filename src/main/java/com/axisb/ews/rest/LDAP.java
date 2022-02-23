package com.axisb.ews.rest;

import java.util.HashMap;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.springframework.beans.factory.annotation.Value;

public class LDAP {
	public static HashMap<String, String> authenticate(String username, String password,boolean ldap) {
		HashMap<String, String> details = null;
		System.out.println("ldap"+ldap);
		if (ldap) {
			if (username.equals("187376") && password.equals("")) {
				// authenticationManager.authenticate(new
				// UsernamePasswordAuthenticationToken(username, password));
				details = new HashMap<String, String>();
				details.put("name", "Abhishek Suman");
				// TODO
				return details;
			}
			if (username.equals("4577")) {
				// authenticationManager.authenticate(new
				// UsernamePasswordAuthenticationToken(username, password));
				details = new HashMap<String, String>();
				details.put("name", "Sunil");
				// TODO
				return details;
			}
		} else {
			
			LdapContext ctx=null;
			
			try {
				Hashtable env= new Hashtable();
				env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
				env.put(Context.SECURITY_AUTHENTICATION, "Simple");
				env.put(Context.SECURITY_PRINCIPAL, "AXISB\\"+username);
				env.put(Context.SECURITY_CREDENTIALS, password);
				env.put(Context.PROVIDER_URL, "ldap://10.9.80.6:389");
				System.out.println("connecting..");
				ctx=new InitialLdapContext(env, null);
				System.out.println("ctx "+ctx);
				String name=printUSerBaseAttributes(username,ctx);
				System.out.println("name "+name);
				
				details=new HashMap<String, String>();
				details.put("name", name);
			}catch (Exception e) {
				System.out.println("error1 UA");
				System.out.println(e.getMessage());
			}		
		}
		return details;
	}

	private static String printUSerBaseAttributes(String username, LdapContext ctx) throws Exception{
		String name="";
		
			SearchControls constraints=new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String[] attrIDs= {"sn","givenname","sAMAccountName"};
			constraints.setReturningAttributes(attrIDs);
			
			NamingEnumeration answer =ctx.search("DC=axisb,DC=com", "sAMAccountName="+username,constraints);
			
			if(answer.hasMore())
			{
				Attributes attrs=((SearchResult)answer.next()).getAttributes();
				name=" "+attrs.get("givenname").toString().replace("givenName:", "")+" "+attrs.get("sn").toString().replace("sn:", "");
			}
			
		
		
		return name;
	}
}
