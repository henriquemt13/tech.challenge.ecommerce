create sequence user_auth_seq;
create table user_auth
(
    id                  bigint primary key not null DEFAULT nextval('user_auth_seq'),
    username            varchar(60),
    password            varchar(255),
    has_root_permission boolean
);
ALTER SEQUENCE user_auth_seq
    OWNED BY user_auth.id;


create sequence user_data_seq;
create table user_data
(
    id           bigint primary key not null DEFAULT nextval('user_data_seq'),
    id_user_auth bigint,
    email        varchar(255) unique,
    constraint fk_user_auth
        foreign key (id_user_auth)
            references user_auth (id)
);
ALTER SEQUENCE user_data_seq OWNED BY user_data.id;

create sequence item_seq;
create table item
(
    id          bigint primary key not null DEFAULT nextval('item_seq'),
    name        varchar(100),
    description varchar(255),
    type        varchar(15),
    created_at  timestamp,
    updated_at  timestamp
);
ALTER SEQUENCE item_seq OWNED BY item.id;

create sequence purchase_bag_seq;
create table purchase_bag
(
    id           bigint primary key not null DEFAULT nextval('purchase_bag_seq'),
    id_user_data bigint,
    id_item      bigint,
    quantity     int,
    created_at   timestamp,
    updated_at   timestamp,
    constraint fk_user_data
        foreign key (id_user_data)
            references user_data (id),
    constraint fk_item
        foreign key (id_item)
            references item (id)
);
ALTER SEQUENCE purchase_bag_seq OWNED BY purchase_bag.id;