CREATE TABLE IF NOT EXISTS permission (
    rootid SERIAL PRIMARY KEY,
    active BOOLEAN DEFAULT TRUE,
    permission VARCHAR(32) UNIQUE
);

/* Default Data Load for permissions */
INSERT INTO permission (rootid, permission) 
VALUES
    (19011996, 'SuperUser'),
    (28072023, 'Admin'),
    (22101999, 'CustomerSupport'),
    (789, 'ManageUsers'),
    (5099, 'EditUsers'),
    (22, 'EditOrders'),
    (218, 'ManageOrders'),
    (18, 'EditPromotions'),
    (9890, 'ManageProducts'),
    (1064, 'EditProducts'),
    (2012, 'ManageCategories'),
    (28, 'EditCategories'),
    (897, 'PointOfSale'),
    (148, 'ManageReporting'),
    (150, 'ManagePromotions'),
    (710, 'ManageCoupons'),
    (1765, 'EditCoupons'),
    (1965, 'ManageSupplier');
