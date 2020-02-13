insert into functions (id, name, url, icon, sorted) select replace(uuid(), '-', ''), '收支表', 'account-book/content', 'icon-edit', 1 from dual;
insert into functions (id, name, sorted) select replace(uuid(), '-', ''), '圖表', 2 from dual;
insert into functions (id, name, url, parent_id, sorted) select replace(uuid(), '-', ''), '長條圖', 'chart/bar', id, 1 from functions where name = '圖表';
insert into functions (id, name, url, parent_id, sorted) select replace(uuid(), '-', ''), '圓餅圖', 'chart/pie', id, 2 from functions where name = '圖表';
insert into functions (id, name, sorted) select replace(uuid(), '-', ''), '設定', 3 from dual;
insert into functions (id, name, url, parent_id, sorted) select replace(uuid(), '-', ''), '帳戶', 'account/content', id, 1 from functions where name = '設定';
insert into functions (id, name, url, parent_id, sorted) select replace(uuid(), '-', ''), '項目', 'item/content', id, 2 from functions where name = '設定';
insert into functions (id, name, url, sorted) select replace(uuid(), '-', ''), '登出', 'logout', 4 from dual;