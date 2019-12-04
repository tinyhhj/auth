insert into oauth_client (client_id, resource_ids, client_secret, scope, authorized_grant_types,web_server_redirect_uri,
authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
values( 'client', null, '{noop}password', 'read', 'authorization_code,password,client_credentials,implicit,refresh_token'
 ,'http://localhost:8080/test/endpoint', 'ROLE_TEST', 300, 60*60*24*30, null, 'false');