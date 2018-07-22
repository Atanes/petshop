insert into permissao (id, nome) values 
	(1, 'ROLE_OS_ATENDIMENTO')
	, (2, 'ROLE_OS_ORCAMENTO')
	, (3, 'ROLE_OS_TECNICO')
	, (4, 'ROLE_OS_ADMIN')
;
	
insert into usuario (id, nome, login, senha, ativo) values 
	(1, 'Atanes', 'atanes', '$2a$10$JvyF9q/k/eYwXTVjc4Ay0OT/dCwjW14eT88q3e587jaENTvtt30s2', true)
	, (2, 'Carlos', 'carlos', '$2a$10$JvyF9q/k/eYwXTVjc4Ay0OT/dCwjW14eT88q3e587jaENTvtt30s2', true)
	, (3, 'Técnico', 'tecnico', '$2a$10$JvyF9q/k/eYwXTVjc4Ay0OT/dCwjW14eT88q3e587jaENTvtt30s2', true)
	, (4, 'Administrador', 'admin', '$2a$10$JvyF9q/k/eYwXTVjc4Ay0OT/dCwjW14eT88q3e587jaENTvtt30s2', true)
	, (5, 'Atendente', 'atendente', '$2a$10$JvyF9q/k/eYwXTVjc4Ay0OT/dCwjW14eT88q3e587jaENTvtt30s2', true)
	, (6, 'Orcamento', 'orcamento', '$2a$10$JvyF9q/k/eYwXTVjc4Ay0OT/dCwjW14eT88q3e587jaENTvtt30s2', true)
;
	
insert into usuario_permissao (usuario_id, permissao_id) values 
	(1, 4)
	, (2, 4)
	, (3, 3)
	, (4, 4)
	, (5, 1)
	, (6, 2)
;










--insert into grupo (id, nome) values 
--	(1, 'DIRETOR') 
--	, (2, 'GERENTE')
--;
	
--insert into usuario_grupo (usuario_id, grupo_id) values 
--	(1, 1)
--	, (2, 2)
--;
	
--insert into grupo_permissao (grupo_id, permissao_id) values 
--	(1, 1)
--	, (1, 2)
--	, (1, 3)
--	, (2, 1)
--	, (2, 2)
--;