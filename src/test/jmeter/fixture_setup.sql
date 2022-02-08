--
-- PostgreSQL database dump
--

-- Dumped from database version 12.9 (Debian 12.9-1.pgdg110+1)
-- Dumped by pg_dump version 14.1

-- Started on 2022-02-05 22:49:32

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;


INSERT INTO public.users (id, password, type, username, canteen_id) VALUES (4, 'e9749632c9795cbb006f73c9dff929da42ce03ae218e21593d4e6c1f1ff8241c', 2, 'user1', NULL);
INSERT INTO public.users (id, password, type, username, canteen_id) VALUES (5, '91bd699f617dca3c983ab9d39c56087379f4e684cb8167bb1ed477158a2dbb50', 2, 'user2', NULL);
INSERT INTO public.users (id, password, type, username, canteen_id) VALUES (6, '432c2008d5887d13d7fdda9cb2668e19c1e7b1bc1e360ef0db3c348c8d16443f', 2, 'user3', NULL);
INSERT INTO public.users (id, password, type, username, canteen_id) VALUES (7, 'a0d09b672c2a13501b9e65382c7030f35e542c2a5e83452dc4d30a423d6eb228', 2, 'user4', NULL);
INSERT INTO public.users (id, password, type, username, canteen_id) VALUES (8, '20f065f21592f1052a59ccf59fe9c0d28533321cf4c86836a0bbfe4463fa0c53', 2, 'user5', NULL);
INSERT INTO public.users (id, password, type, username, canteen_id) VALUES (9, '5e3a8134798028531d7acbaadd040ca894e68607552092d62399283a3018b5ce', 2, 'user6', NULL);
INSERT INTO public.users (id, password, type, username, canteen_id) VALUES (10, '47d234e65a6adb39df10136a38976b0f641b29acccf6b2bfb0fdeb95f1c304b3', 2, 'user7', NULL);
INSERT INTO public.users (id, password, type, username, canteen_id) VALUES (11, '0514939cb14d52fc4c3d8eca10b30f2784b9a78b8715eab917b43649fc70469f', 2, 'user8', NULL);
INSERT INTO public.users (id, password, type, username, canteen_id) VALUES (12, 'c497ed33d4d146c6cc8037c548b9ab5adb73122c5a3457a71dd8e43bbd55ace3', 2, 'user9', NULL);
INSERT INTO public.users (id, password, type, username, canteen_id) VALUES (13, 'af4c5857a846e0d24c12dd90b35ad9bde245e5a52b7d453d3ab1a9e5d81ecd33', 2, 'user0', NULL);
INSERT INTO public.users (id, password, type, username, canteen_id) VALUES (14, 'af4c5857a846e0d24c12dd90b35ad9bde245e5a52b7d453d3ab1a9e5d81ecd33', 1, 'admin0', NULL);
INSERT INTO public.users (id, password, type, username, canteen_id) VALUES (15, 'af4c5857a846e0d24c12dd90b35ad9bde245e5a52b7d453d3ab1a9e5d81ecd33', 1, 'admin1', NULL);
INSERT INTO public.users (id, password, type, username, canteen_id) VALUES (16, 'af4c5857a846e0d24c12dd90b35ad9bde245e5a52b7d453d3ab1a9e5d81ecd33', 1, 'admin2', NULL);
INSERT INTO public.users (id, password, type, username, canteen_id) VALUES (17, 'af4c5857a846e0d24c12dd90b35ad9bde245e5a52b7d453d3ab1a9e5d81ecd33', 1, 'admin3', NULL);
INSERT INTO public.users (id, password, type, username, canteen_id) VALUES (18, 'af4c5857a846e0d24c12dd90b35ad9bde245e5a52b7d453d3ab1a9e5d81ecd33', 1, 'admin4', NULL);

--
-- TOC entry 3018 (class 0 OID 16445)
-- Dependencies: 213
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3016 (class 0 OID 16436)
-- Dependencies: 211
-- Data for Name: order_has_dish; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3024 (class 0 OID 0)
-- Dependencies: 202
-- Name: auth_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.auth_id_seq', 1, false);


--
-- TOC entry 3025 (class 0 OID 0)
-- Dependencies: 204
-- Name: canteen_canteen_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.canteen_canteen_id_seq', 2, true);


--
-- TOC entry 3026 (class 0 OID 0)
-- Dependencies: 206
-- Name: dish_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.dish_id_seq', 1, false);


--
-- TOC entry 3027 (class 0 OID 0)
-- Dependencies: 210
-- Name: order_has_dish_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.order_has_dish_id_seq', 1, false);


--
-- TOC entry 3028 (class 0 OID 0)
-- Dependencies: 212
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.orders_id_seq', 1, false);


--
-- TOC entry 3029 (class 0 OID 0)
-- Dependencies: 208
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.users_id_seq', 999, true);


-- Completed on 2022-02-05 22:49:33

--
-- PostgreSQL database dump complete
--

