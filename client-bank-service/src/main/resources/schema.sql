CREATE TABLE clients (
                         id VARCHAR(50) PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         balance DOUBLE PRECISION NOT NULL
);

INSERT INTO clients (id, name, balance) VALUES
('cli001', 'Engueran', 1200.99),
('cli002', 'Gabin',   20.00),
('cli003', 'Thomas', 829.90),
('cli004', 'Alexis',   1592.90);
