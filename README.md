# FirebaseMemo
firebaseのデータベースfirestoreを使用したメモ帳です。

https://github.com/RUSSi28/Datebase/assets/110999990/77cb3fd9-ef09-4943-993a-ce585db50e6f

使用方法
1.メモの追加
  メモはbottom app barの左から3つ目の要素を選択し、titleフィールドとtextフィールドに
  それぞれタイトルとメモしたい内容を記入し、addボタンを押すことでデータベースに追加されます。
  
2.メモの閲覧
  メモの閲覧はbottom app barの左から1つ目の要素を選択することで画面に表示されます。(正しくは画面遷移bottom navegation)
  
3.メモの削除
  メモの削除はbottom app barの左から2つ目の要素を選択することで、"削除する"の文字列がtext bottonに表示されたメモの
  リストが表示され、各々の削除ボタンはそのメモの上部にtext buttonとして存在するため、ボタンを押すことによってメモの削除が行える。
  (再度表示画面に向かうことで削除が確認できる)
  

使用している技術
・jetpack composeライブラリ
・firebase(fire store)
