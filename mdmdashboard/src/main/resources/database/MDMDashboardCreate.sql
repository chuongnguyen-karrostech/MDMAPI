create table if not exists public.tabletsqlite
(
    id bigint generated always as identity,
    devserialnumber character varying(50),
    syncdate timestamp without time zone,
    updatetime timestamp without time zone,
    constraint pkey_tabletsqlite primary key (id)
);

create table if not exists public.punchlog
(
    id bigint generated always as identity,
    activitycode integer,
    busid character varying(50),
    driverid character varying(50),
    billingid character varying(4),
    latitude double precision,
    longitude double precision,  
    lastsynclatitude double precision,
    lastsynclongitude double precision,
    lastsynctype character varying(50),
    lastsynctime timestamp without time zone,
    receivetime timestamp without time zone,
    internaltime timestamp without time zone,
    punchtime timestamp without time zone,
    sendtime timestamp without time zone,
    devserialnumber character varying(50),
    constraint pkey_punchlog primary key (id)
);

create table if not exists public.invalidlocation
(
    id bigint generated always as identity,
    devserialnumber character varying(50),
    noofworking integer,
    noofceiling integer,
    noofbeforeceiling integer,
    updatetime timestamp without time zone,
    messagedate timestamp without time zone,
    constraint pkey_invalidlocation primary key (id)
);

create table if not exists public.wificellularusage
(
    id bigint generated always as identity,
    devserialnumber character varying(50),
    totaldata double precision,
    rectype character varying(20),
    repdate date,
    constraint pkey_wificellularusage primary key (id)
);