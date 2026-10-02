INSERT INTO users (full_name, email, username, password, role) VALUES
('System Administrator', 'admin@school.edu', 'admin', 'admin123', 'ADMIN'),
('Aisha Khan', 'aisha@school.edu', 'aisha', 'student123', 'STUDENT'),
('Michael Lee', 'michael@school.edu', 'michael', 'student123', 'STUDENT'),
('Sarah Brown', 'sarah@school.edu', 'sarah', 'staff123', 'STAFF');

INSERT INTO items (title, description, category, item_type, location_found, date_reported, status, image_url, owner_id, finder_id, created_by) VALUES
('Black Wallet', 'Leather wallet with student ID and cash inside.', 'Accessories', 'LOST', 'Library Study Hall', CURRENT_TIMESTAMP, 'OPEN', 'https://images.unsplash.com/photo-1627123424574-724758594e93?auto=format&fit=crop&w=800&q=80', 2, NULL, 2),
('Blue Water Bottle', 'Insulated bottle with school logo.', 'Personal Items', 'FOUND', 'Biology Lab', CURRENT_TIMESTAMP, 'OPEN', 'https://images.unsplash.com/photo-1602143407151-7111542de6e8?auto=format&fit=crop&w=800&q=80', NULL, 3, 3),
('Science Notebook', 'Notebooks with chemistry notes and name label.', 'Stationery', 'LOST', 'Main Corridor', CURRENT_TIMESTAMP, 'OPEN', 'https://images.unsplash.com/photo-1517849845537-4d257902454a?auto=format&fit=crop&w=800&q=80', 4, NULL, 4),
('Silver Keychain', 'Silver key chain with a house key and tag.', 'Accessories', 'FOUND', 'Gym Entrance', CURRENT_TIMESTAMP, 'OPEN', 'https://images.unsplash.com/photo-1521572267360-ee0c2909d518?auto=format&fit=crop&w=800&q=80', NULL, 1, 1);

INSERT INTO claims (item_id, claimant_id, notes, status) VALUES
(1, 3, 'I believe this is mine. I can provide student ID details.', 'PENDING'),
(4, 2, 'This keychain matches my set of keys.', 'PENDING');

INSERT INTO messages (sender_id, receiver_id, item_id, content) VALUES
(2, 3, 1, 'I reported a black wallet. Is it yours?'),
(3, 2, 1, 'Yes, I found it near the library.')
;