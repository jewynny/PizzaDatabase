CREATE INDEX idx_orderID
ON FoodOrder USING BTREE (orderID);

CREATE INDEX idx_storeID
ON Store USING BTREE (storeID);

CREATE INDEX idx_Items
ON Items USING BTREE (itemName);