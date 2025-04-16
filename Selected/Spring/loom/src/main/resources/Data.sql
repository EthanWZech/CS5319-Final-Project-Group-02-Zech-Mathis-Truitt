-- threads
INSERT INTO thread (publish_date, upvotes, downvotes, topic, username, title, text, image)
VALUES
    ('2025-01-15', 52, 23, 'sports', 'SportsGuy', 'This Football!',
     'Here is some text about a football', NULL),
    ('2025-01-30', 12, 2, 'sports', 'SportsGuy', 'Those Lakers...',
     'They sure do play that ballgame', NULL),
    ('2025-03-05', 30, 5, 'fishing', 'EpicCatch', 'That time I caught a big one',
     'It was thiiiiiiiis big', NULL),
    ('2025-04-01', 112, 3, 'pics', 'Vista', 'Great View',
     'Caught this',  'https://www.muchbetteradventures.com/magazine/content/images/size/w2000/2019/06/13091225/iStock-157644719.jpg');

-- comments
INSERT INTO comment (publish_date, upvotes, downvotes, username, text, image, thread_id, parent_comment_id)
VALUES
    ('2025-01-15', 12, 1, 'Flips', 'I think I''ll get one too', NULL,
     1, NULL),
    ('2025-01-16', 6, 0, 'SportsGuy', 'Best call of your life', NULL,
     1, 1),
    ('2025-02-01', 20, 4, 'Vista', 'Here''s a picture',
     'https://sbvpa.org/wp-content/uploads/2022/08/sports-tools_53876-138077-768x501.jpg',
     2, NULL),
    ('2025-02-02', 6, 0, 'TempMan', 'Here''s a reply to an image', NULL,
     2, 3),
    ('2025-02-03', 6, 0, 'Volcano', 'Here''s a second reply to an image', NULL,
     2, 3),
    ('2025-02-02', 6, 0, 'Wildling', 'This thread has a lot of replies', NULL,
     2, NULL),
    ('2025-02-04', 6, 0, 'Indigo', 'And this is a reply to a reply', NULL,
     2, 4);

