-- Inserta la Fuente Dinámica si no existe una con el nombre 'Fuente Dinámica'
INSERT INTO fuente (nombre, tipo_fuente, base_url, ultimaActualizacion)
SELECT 'Fuente Dinámica',
       'DINAMICA',
       'http://localhost:8082',
       NULL WHERE NOT EXISTS (SELECT 1 FROM fuente WHERE nombre = 'Fuente Dinámica');