insert into User (id, name, password) values
  (1, 'admin', '$2a$10$hnv29n7cYMoQrd5RFyl9iust3Mmq6jMkyx8WPcnkR4rQp1BX338GS'),
  (2, 'user', '$2a$10$3IgUC//AzyMvEbe3GJ4xGuiftuY544DAGtRpF3IPs.mpFeJgjeDJC');

insert into Role (id, name) values
  (1, 'ROLE_ADMIN'),
  (2, 'ROLE_USER');

insert into User_Role (User_id, roles_id) values
  (1, 1),
  (1, 2),
  (2, 2);

insert into MediaFile (duration, name, views, fileStorageLocation, uuid, serveUrl, owner_id)
values
       ( 100, 'entry1', 5, 'entry1.mp4', 'f1', 'entry1.mp4', 1 ),
       ( 150, 'entry2', 5, 'entry2.mp4', 'f2', 'entry2.mp4', 1 ),
       ( 200, 'entry3', 5, 'entry3.mp4', 'f3', 'entry3.mp4', 1 ),
       ( 50,  'entry4', 5, 'entry4.mp4', 'f4', 'entry4.mp4', 1 ),
       ( 50,  'entry5', 5, 'entry5.mp4', 'f5', 'entry5.mp4', 1 );
