create table if not exists public.site
(
    site_id integer generated always as identity,
    site_name character varying(50) not null,    
    site_tenanid character varying(50) null,
    constraint pk_site_autoid primary key (site_id),
    constraint unique_site_tenanid unique (site_tenanid),
    constraint unique_site_name unique (site_name)
);

alter table public.site add if not exists site_forwardurl character varying(500) null;
alter table public.site add if not exists site_dashboardurl character varying(500) null;

create table if not exists public.tablet
(
    tablet_id integer generated always as identity,
    tablet_serialno character varying(50) not null,
    site_id integer not null,
    constraint pk_tablet_id primary key (tablet_id),
    constraint unique_tablet_serialno unique (tablet_serialno),
	constraint fk_siteid foreign key (site_id) references public.site (site_id) match simple on update no action on delete cascade
);

create table if not exists public.wifi
(
    wifi_id integer generated always as identity,
    wifi_ssid character varying(50) not null,
    wifi_password character varying(50) not null,
    wifi_securitytype character varying(50) not null,
    wifi_longitude float not null,
    wifi_latitude float not null,
    wifi_radius float not null,
    site_id integer not null,
    constraint pk_wifi_id primary key (wifi_id),
	constraint fk_siteid foreign key (site_id) references public.site (site_id) match simple on update no action on delete cascade
);

create table if not exists public.url
(
    url_id integer generated always as identity,
    url_path character varying(500) not null,
    url_version character varying(20) not null,    
    url_type character varying(20) not null,
    url_file_size integer,
    site_id integer not null,    
    constraint pk_url_id primary key (url_id),
    constraint fk_siteid foreign key (site_id) references public.site (site_id) match simple on update no action on delete cascade
);
alter table url add column if not exists url_file_size integer;

create table if not exists public.trkdpapk
(
    trk_id bigserial not null,
    trk_tabletserial character varying(50),
    trk_updateavailable boolean,
    trk_currentversion character varying(20),    
    trk_downattemp integer,
    trk_timeattemp timestamp without time zone,   
    trk_downloaded boolean,
    trk_upgraded boolean,
    trk_wificonnect timestamp without time zone,    
    constraint pk_trkdpapk primary key (trk_id),
    constraint unique_trkdpapk_trk_tabletserial unique (trk_tabletserial),
	constraint fk_trk_tabletserial foreign key (trk_tabletserial) references public.tablet (tablet_serialno) match simple on update cascade on delete cascade
);

create table if not exists public.trkdpmap
(
    trk_id bigserial not null,
    trk_tabletserial character varying(50),
    trk_updateavailable boolean,
    trk_currentversion character varying(20),    
    trk_downattemp integer,
    trk_timeattemp timestamp without time zone, 
    trk_downloaded boolean,
    trk_upgraded boolean,
    trk_wificonnect timestamp without time zone,    
    constraint pk_trkdpmap primary key (trk_id),
    constraint unique_trkdpmap_trk_tabletserial unique (trk_tabletserial),
	constraint fk_trk_tabletserial foreign key (trk_tabletserial) references public.tablet (tablet_serialno) match simple on update cascade on delete cascade
);

create table if not exists public.trkosmapk
(
    trk_id bigserial not null,
    trk_tabletserial character varying(50),
    trk_updateavailable boolean,
    trk_currentversion character varying(20),    
    trk_downattemp integer,
    trk_timeattemp timestamp without time zone,   
    trk_downloaded boolean,
    trk_upgraded boolean,
    trk_wificonnect timestamp without time zone,    
    constraint pk_trkosmapk primary key (trk_id),
    constraint unique_trkosmapk_trk_tabletserial unique (trk_tabletserial),
	constraint fk_trk_tabletserial foreign key (trk_tabletserial) references public.tablet (tablet_serialno) match simple on update cascade on delete cascade
);

create table if not exists public.trkosmmap
(
    trk_id bigserial not null,
    trk_tabletserial character varying(50),
    trk_updateavailable boolean,
    trk_currentversion character varying(20),    
    trk_downattemp integer,
    trk_timeattemp timestamp without time zone,   
    trk_downloaded boolean,
    trk_upgraded boolean,
    trk_wificonnect timestamp without time zone,    
    constraint pk_osmmap primary key (trk_id),
    constraint unique_trkosmmap_trk_tabletserial unique (trk_tabletserial),
	constraint fk_trk_tabletserial foreign key (trk_tabletserial) references public.tablet (tablet_serialno) match simple on update cascade on delete cascade
);

create unique index if not exists idx_trkdpapk_tabletserial on public.trkdpapk (trk_tabletserial);
create unique index if not exists idx_trkdpmap_tabletserial on public.trkdpmap (trk_tabletserial);
create unique index if not exists idx_trkosmapk_tabletserial on public.trkosmapk (trk_tabletserial);
create unique index if not exists idx_trkosmmap_tabletserial on public.trkosmmap (trk_tabletserial);

create index if not exists idx_tablet_site_id on public.tablet (site_id);
create index if not exists idx_url_site_id on public.url (site_id);
create index if not exists idx_wifi_site_id on public.wifi (site_id);

create table if not exists public.log
(
    log_id bigserial not null,
	log_date timestamp without time zone,   
	log_site character varying(100),
    log_message character varying(5000),
	log_description text,
	log_created_date timestamp without time zone
);

create index if not exists idx_logs_date on public.log (log_date);