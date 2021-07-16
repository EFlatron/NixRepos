create table locations
(
    id       serial primary key,
    location text not null
);


insert into locations (location)
values ('gdansk'),
       ('bydgoszcz'),
       ('torun'),
       ('warszawa');

create table problems
(
    id      int not null unique primary key,
    from_id int references locations (id),
    to_id   int references locations (id)
);

create table routes
(
    id      SERIAL,
    from_id int references locations (id),
    to_id   int references locations (id),
    cost    int
);

insert into routes (from_id, to_id, cost)
values ('1', '2', '1'),
       ('1', '3', '3'),
       ('2', '1', '1'),
       ('2', '3', '1'),
       ('2', '4', '4'),
       ('3', '1', '3'),
       ('3', '2', '1'),
       ('3', '4', '1'),
       ('4', '2', '4'),
       ('4', '3', '1');

create table solutions
(
    problem_id int references problems (id)
        on delete cascade,
    cost       int default null
);

insert into problems (id, from_id, to_id)
values (1, 1, 4),
       (2, 2, 4);