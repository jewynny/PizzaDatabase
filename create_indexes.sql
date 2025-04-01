


-- for counting how many parts in nyc have more than 70 on_hand
CREATE INDEX idx_part_nyc_on_hand
ON part_nyc USING BTREE (on_hand);

-- for counting total red parts on_hand
CREATE INDEX idx_part_nyc_color
ON part_nyc USING BTREE (color);    -- index for parts NYC on hand

CREATE INDEX idx_part_sfo_color
ON part_sfo USING BTREE (color);    -- index for parts sfo on hand

CREATE INDEX idx_color_name
ON color USING BTREE (color_name);  -- index for colors since we look for red

-- indexing for suppliers who have more parts in nyc
CREATE INDEX idx_supplier_id
ON supplier USING BTREE (supplier_id);  -- index for supplier id

CREATE INDEX idx_part_nyc_supplier
ON part_nyc USING BTREE (supplier);     -- index for nyc supplier

CREATE INDEX idx_part_sfo_supplier
ON part_sfo USING BTREE (supplier);     -- index for nyc supplier

-- indexing for all suppliers that supply parts in NYC that aren’t supplied by anyone in SFO
CREATE INDEX idx_part_nyc_part_number
ON part_nyc USING BTREE (part_number);  -- indexing part numbers 

CREATE INDEX idx_part_sfo_part_number
ON part_sfo USING BTREE (part_number);  -- indexing part numbers
