INSERT INTO category (category_id, name, type) VALUES
                                                     (1, 'Salary', 'INCOME'),
                                                     (2, 'Freelance', 'INCOME'),
                                                     (3, 'Interest', 'INCOME'),
                                                     (4, 'Dividends', 'INCOME'),
                                                     (5, 'Rent', 'EXPENSE'),
                                                     (6, 'Groceries', 'EXPENSE'),
                                                     (7, 'Electricity', 'EXPENSE'),
                                                     (8, 'Transportation', 'EXPENSE'),
                                                     (9, 'Emergency Fund', 'SAVING'),
                                                     (10, 'Vacation Fund', 'SAVING'),
                                                     (11, 'Retirement Fund', 'SAVING'),
                                                     (12, 'Education Fund', 'SAVING')
ON CONFLICT (category_id) DO NOTHING;
