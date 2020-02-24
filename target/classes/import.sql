
insert into ACCOUNT_TYPE (id, name, description) values(1,'customer', 'customer account');
insert into ACCOUNT_TYPE (id, name, description) values(2,'business', 'business account');


insert into CUSTOMER(id, first_name, last_name, address, contact_number, date_of_birth) values(600,'Deepthi','Bolla','Street 1, Edinburgh', '712345678', '1990-01-01');

insert into Account(id, account_type_id, active, balance, customer_id) values(10001,1,TRUE, 2000, 600);
insert into Account(id, account_type_id, active, balance, customer_id) values(10002,1, TRUE, 3000, 600);
insert into Account(id, account_type_id, active, balance, customer_id) values(10003,1, FALSE, 3000, 600);

insert into CARD_TYPE(id, type, description, interest_rate) values(3,'debit', 'customer debit card', 1.50);
insert into CARD_TYPE(id, type, description, interest_rate) values(4,'credit', 'customer credit card', 4.50);

insert into BANK_CARD(id, card_type_id, customer_name, valid_from, expiry_end, security_code, account_id) values(10003, 3, 'deepthi', '2012-01-01', '2012-01-01', 123, 10001);
insert into BANK_CARD(id, card_type_id, customer_name, valid_from, expiry_end, security_code, account_id) values(10004, 4, 'deepthi', '2012-01-01', '2012-01-01', 456, 10001);

insert into BANK_TRANSACTION(id, from_account_id, to_account_id, description, amount, transaction_date) values(101,10002,10001,'pizza bill', 5, '2015-01-01');
