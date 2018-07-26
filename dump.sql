--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4 (Ubuntu 10.4-0ubuntu0.18.04)
-- Dumped by pg_dump version 10.4 (Ubuntu 10.4-0ubuntu0.18.04)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: json; Type: SCHEMA; Schema: -; Owner: vasyaas
--

CREATE SCHEMA json;


ALTER SCHEMA json OWNER TO vasyaas;

--
-- Name: idgen; Type: SEQUENCE; Schema: json; Owner: vasyaas
--

CREATE SEQUENCE json.idgen
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE json.idgen OWNER TO vasyaas;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: person; Type: TABLE; Schema: json; Owner: vasyaas
--

CREATE TABLE json.person (
    id bigint DEFAULT nextval('json.idgen'::regclass) NOT NULL,
    email character varying(50) NOT NULL,
    pass character varying(50)
);


ALTER TABLE json.person OWNER TO vasyaas;

--
-- Name: vk; Type: TABLE; Schema: json; Owner: vasyaas
--

CREATE TABLE json.vk (
    id integer NOT NULL,
    vkid character varying(255) NOT NULL,
    email character varying(50) NOT NULL
);


ALTER TABLE json.vk OWNER TO vasyaas;

--
-- Data for Name: person; Type: TABLE DATA; Schema: json; Owner: vasyaas
--

COPY json.person (id, email, pass) FROM stdin;
30	vasya_97@bk.ru	\N
33	misha@mail.ru	d8578edf8458ce06fbc5bb76a58c5ca4
\.


--
-- Data for Name: vk; Type: TABLE DATA; Schema: json; Owner: vasyaas
--

COPY json.vk (id, vkid, email) FROM stdin;
30	9491440	vasya_97@bk.ru
\.


--
-- Name: idgen; Type: SEQUENCE SET; Schema: json; Owner: vasyaas
--

SELECT pg_catalog.setval('json.idgen', 33, true);


--
-- Name: person person_pkey; Type: CONSTRAINT; Schema: json; Owner: vasyaas
--

ALTER TABLE ONLY json.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- Name: vk vk_email_key; Type: CONSTRAINT; Schema: json; Owner: vasyaas
--

ALTER TABLE ONLY json.vk
    ADD CONSTRAINT vk_email_key UNIQUE (email);


--
-- Name: vk vk_pkey; Type: CONSTRAINT; Schema: json; Owner: vasyaas
--

ALTER TABLE ONLY json.vk
    ADD CONSTRAINT vk_pkey PRIMARY KEY (id);


--
-- Name: vk vk_vkid_key; Type: CONSTRAINT; Schema: json; Owner: vasyaas
--

ALTER TABLE ONLY json.vk
    ADD CONSTRAINT vk_vkid_key UNIQUE (vkid);


--
-- Name: vk vk_fk0; Type: FK CONSTRAINT; Schema: json; Owner: vasyaas
--

ALTER TABLE ONLY json.vk
    ADD CONSTRAINT vk_fk0 FOREIGN KEY (id) REFERENCES json.person(id);


--
-- Name: vk vk_fk1; Type: FK CONSTRAINT; Schema: json; Owner: vasyaas
--

ALTER TABLE ONLY json.vk
    ADD CONSTRAINT vk_fk1 FOREIGN KEY (id) REFERENCES json.vk(id);


--
-- Name: SEQUENCE idgen; Type: ACL; Schema: json; Owner: vasyaas
--

GRANT ALL ON SEQUENCE json.idgen TO PUBLIC;


--
-- PostgreSQL database dump complete
--

