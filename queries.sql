--Count how many parts in NYC have more than 70 parts on_hand
SELECT SUM(n.on_hand) AS NYC_SUM_PARTS_GREATER_THAN_70
FROM part_nyc n
WHERE n.on_hand > 70;

--Count how many total parts on_hand, in both NYC and SFO, are Red

SELECT SUM(n.on_hand) + SUM(s.on_hand) AS total_red_parts_on_hand
FROM part_nyc n, part_sfo s, color c
WHERE n.part_number = s.part_number AND n.color = c.color_id AND s.color = c.color_id AND c.color_name = 'Red';

--List all the suppliers that have more total on_hand parts in NYC than they do in SFO.
SELECT sup.supplier_name AS suppliers_with_more_parts_in_NYC_than_SFO
FROM supplier sup
JOIN part_nyc n ON sup.supplier_id = n.supplier
LEFT JOIN part_sfo s ON n.part_number = s.part_number AND n.supplier = s.supplier
GROUP BY sup.supplier_name
HAVING SUM(n.on_hand) > SUM(s.on_hand);

-- List all suppliers that supply parts in NYC that aren’t supplied by anyone in SFO.
SELECT DISTINCT sup.supplier_name AS suppliers_with_parts_in_NYC_not_SFO
FROM supplier sup
JOIN part_nyc n ON sup.supplier_id = n.supplier
WHERE n.part_number NOT IN (SELECT part_number FROM part_sfo);

-- Update all of the NYC on_hand values to on_hand - 10.
UPDATE part_nyc
SET on_hand = on_hand - 10;

-- Delete all parts from NYC which have less than 30 parts on_hand.
DELETE FROM part_nyc
WHERE on_hand < 30;
