SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


CREATE TABLE `client` (
  cellphone varchar(15) DEFAULT NULL,
  address varchar(500) DEFAULT NULL,
  document varchar(255) NOT NULL,
  email varchar(255) DEFAULT NULL,
  full_name varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO client (cellphone, address, document, email, full_name) VALUES
('3222379887', 'El dorado', '1014478353', 'example@mail.com', 'Andres Yate');

CREATE TABLE order_entity (
  cant int(11) DEFAULT NULL,
  iva double DEFAULT NULL,
  ordered tinyint(1) DEFAULT 0,
  sub_total double DEFAULT NULL,
  total double DEFAULT NULL,
  date_order datetime(6) DEFAULT NULL,
  date_ordered datetime(6) DEFAULT NULL,
  id bigint(20) NOT NULL,
  product_uuid binary(38) DEFAULT NULL,
  info_additional varchar(511) DEFAULT NULL,
  client_document varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO order_entity (cant, iva, ordered, sub_total, total, date_order, date_ordered, id, product_uuid, info_additional, client_document) VALUES
(2, 19, 0, 5000, 2000, '2024-02-25 16:00:00.000000', '2024-03-01 01:00:00.000000', 1, 0x31643539666436612d646261312d343866332d613161372d3863326131336536646530390000, 'N/A', '1014478353');

CREATE TABLE product (
  availability tinyint(1) DEFAULT 1,
  price double DEFAULT NULL,
  id bigint(20) NOT NULL,
  uuid binary(38) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  category enum('HAMBURGERS_AND_HOTDOGS','CHICKEN','FISH','MEATS','DESSERTS','VEGAN_FOOD','KIDS_MEALS') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO product (availability, price, id, uuid, description, name, category) VALUES
(1, 1500, 1, 0x31643539666436612d646261312d343866332d613161372d3863326131336536646530390000, 'Descripcion no se', 'Pescado', 'FISH');


ALTER TABLE client
  ADD PRIMARY KEY (document),
  ADD UNIQUE KEY UK_b2q8t0h50yjo5n2ogd6wqrsmj (cellphone),
  ADD UNIQUE KEY UK_bfgjs3fem0hmjhvih80158x29 (email);

ALTER TABLE order_entity
  ADD PRIMARY KEY (id),
  ADD KEY FKs51712pn6d82vxaxr9qxakd3b (client_document),
  ADD KEY FK10d58vvchwdhjnk6cp0wicvgv (product_uuid);

ALTER TABLE product
  ADD PRIMARY KEY (id),
  ADD UNIQUE KEY UK_24bc4yyyk3fj3h7ku64i3yuog (uuid);


ALTER TABLE order_entity
  MODIFY id bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

ALTER TABLE product
  MODIFY id bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;


ALTER TABLE order_entity
  ADD CONSTRAINT FK10d58vvchwdhjnk6cp0wicvgv FOREIGN KEY (product_uuid) REFERENCES product (uuid),
  ADD CONSTRAINT FKs51712pn6d82vxaxr9qxakd3b FOREIGN KEY (client_document) REFERENCES client (document);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
