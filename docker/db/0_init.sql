-- создаем пользователя владельца схемы
create user jmb password 'jmb_Q1';

-- создаем схему
create schema jmbs authorization jmb;

-- отзываем у группы public привилегии использования схемы
revoke usage on schema jmbs from public;

-- устанавливаем дефолтную схему пользователю
alter user jmb set search_path = 'jmbs';
