Select * from normalizador_servicio.categoria;
Select * from normalizador_servicio.categorias_externas;

-- GRANIZO
INSERT INTO categoria (nombre) VALUES ('Granizo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Precipitación de granizo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Granizo de gran tamaño');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Fenómeno meteorológico con granizo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Tormenta con piedras de granizo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Granizada destructiva');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Tormenta de granizo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Granizo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Granizada');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Piedra');

-- CALOR EXTREMO
INSERT INTO categoria (nombre) VALUES ('Calor extremo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Calor extremo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Récord histórico de calor');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Emergencia por altas temperaturas');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Fenómeno de calor intenso');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Ola de calor extremo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Temperaturas récord');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Temperaturas sofocantes');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Calor');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Ola de calor');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Altas temperaturas');

-- FRÍO EXTREMO
INSERT INTO categoria (nombre) VALUES ('Frío extremo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Temperaturas bajo cero');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Fenómeno de frío intenso');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Frío extremo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Helada destructiva');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Helada severa');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Helada fuera de temporada');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Congelamiento');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Frío');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Helada');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Bajo cero');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Escarcha');

-- NEVADA
INSERT INTO categoria (nombre) VALUES ('Nevada');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Nevazón');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Tormenta de nieve');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Nevada extrema');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Copiosa caída de nieve');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Nevada histórica');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Ventisca con nieve');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Precipitación de nieve');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Nevada fuera de temporada');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Nieve');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Nevada');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Ventisca');

-- VIENTOS FUERTES
INSERT INTO categoria (nombre) VALUES ('Vientos fuertes');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Ráfagas de más de 100 km/h');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Vientos huracanados');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Vientos con fuerza ciclónica');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Temporal de viento');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Tormenta de viento');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Vendaval');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Tormenta con fuertes vientos');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Ráfagas destructivas');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Viento');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Ráfaga');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Ciclón');

-- INUNDACIÓN
INSERT INTO categoria (nombre) VALUES ('Inundación');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Inundación repentina');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Anegamiento masivo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Inundación por lluvias intensas');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Desborde de arroyo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Crecida de aguas subterráneas');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Inundación en zona urbana');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Desborde de río');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Crecida histórica');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Inundación');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Anegamiento');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Desborde');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Crecida');

-- SEQUÍA
INSERT INTO categoria (nombre) VALUES ('Sequía');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Crisis hídrica');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Escasez de agua');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Emergencia por sequía');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Sequía con pérdidas agrícolas');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Déficit hídrico');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Sequía prolongada');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Sequía extrema');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Sequía');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Falta de agua');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Seca');

-- CENIZA VOLCÁNICA
INSERT INTO categoria (nombre) VALUES ('Ceniza volcánica');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Caída de ceniza');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Emisión volcánica');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Nube de ceniza');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Contaminación por ceniza volcánica');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Polvo volcánico en suspensión');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Lluvia de ceniza volcánica');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Precipitación de material volcánico');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Ceniza');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Volcán');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Erupción');

-- SISMO
INSERT INTO categoria (nombre) VALUES ('Sismo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Sismo con epicentro local');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Evento sísmico');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Movimiento telúrico');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Réplica sísmica');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Sismo de gran magnitud');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Temblor');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Terremoto destructivo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Sismo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Terremoto');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Réplica');

-- INCENDIO FORESTAL
INSERT INTO categoria (nombre) VALUES ('Incendio forestal');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Quema descontrolada');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Fuego en área protegida');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Fuego en bosque nativo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Incendio forestal');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Incendio en reserva natural');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Quema de pastizales');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Fuego arrasador en zona boscosa');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Incendio en zona de monte');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Incendio');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Fuego');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Quema');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Llama');

-- DESLIZAMIENTO
INSERT INTO categoria (nombre) VALUES ('Deslizamiento');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Derrumbe de cerro');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Aluvión de tierra y rocas');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Desprendimiento de ladera');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Alud');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Avalancha de lodo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Deslizamiento de tierra');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Corrimiento de tierra');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Deslizamiento');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Derrumbe');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Aluvión');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Avalancha');

-- TORNADO
INSERT INTO categoria (nombre) VALUES ('Tornado');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Tornado');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Fenómeno de viento rotativo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Torbellino');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Embudo de viento');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Manga de viento');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Remolino de aire');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Remolino');

-- TORMENTA
INSERT INTO categoria (nombre) VALUES ('Tormenta');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Frente tormentoso');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Temporal');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Tormenta eléctrica');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Tormenta tropical');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Tormenta severa');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Tormenta');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Rayo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Trueno');

-- LLUVIA INTENSA
INSERT INTO categoria (nombre) VALUES ('Lluvia intensa');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Lluvia de hielo');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Lluvia torrencial');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Lluvia');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Precipitación');
INSERT INTO categorias_externas (categoria_id, categorias_externas) VALUES (LAST_INSERT_ID(), 'Aguacero');