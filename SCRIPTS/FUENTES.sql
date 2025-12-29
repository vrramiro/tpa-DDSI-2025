INSERT INTO fuente (nombre, tipo_fuente, base_url, ultima_actualizacion)
SELECT 'Fuente Dinámica', 'DINAMICA', 'http://localhost:8082', NULL
    WHERE NOT EXISTS (SELECT 1 FROM fuente WHERE nombre = 'Fuente Dinámica');
INSERT INTO fuente (nombre, tipo_fuente, base_url, ultima_actualizacion)
SELECT 'Fuente Estatica 2', 'ESTATICA', 'http://localhost:8083', NULL
    WHERE NOT EXISTS (SELECT 1 FROM fuente WHERE nombre = 'Fuente Estatica');