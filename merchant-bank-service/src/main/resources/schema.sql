CREATE TABLE merchants (
                           id VARCHAR(50) PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           balance DOUBLE PRECISION NOT NULL
);

INSERT INTO merchants (id, name, balance) VALUES
                                              ('mer001', 'Apple', 124613.00),
                                              ('mer002', 'Samsung', 79722.03),
                                              ('mer003', 'LG', 1203.50),
                                              ('mer004', 'TCL', 12321.00);
