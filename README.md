# TunagappeProto
2015年度開発予定のコミュニケーションアプリ「つながっぺ」のプロトタイプです。下記で想定仕様を説明します。

# 通話部分にGoogleハングアウトを用いた想定仕様について

## クライアントサイド（本レポジトリに起動部分のサンプルコードあり）

GoogleハングアウトのURLを引数にしてIntentでハングアウトアプリを起動します。
会話をしたい2者のユーザーに対して、同じGoogleハングアウトURLを渡すことで同じルームに誘導し、会話をはじめるという仕組みです。
本レポジトリにPushしてあるサンプルコードでは固定のURLを渡してしまっていますが、実際には会話をするユーザーのペアごとに異なるハングアウトURLをサーバーから取得してくる想定です。

浪江町タブレットユーザーはすべてGoogleアカウントが設定済み、かつハングアウトアプリはインストール済みなので、Intentでハングアウトを起動できることは前提としています。（それ以外の、スマートフォンなどで本アプリを利用する場合は、Googleアカウントの設定とハングアウトのインストールはあらかじめ済ませることを案内）
ハングアウトコールが終わるとユーザーは本アプリに戻ってきます。

## サーバーサイド

クライアントサイドからのリクエストを受けて、Google+ Calendar APIを用いてハングアウトURLを生成して取得し、そのURLを返します。

https://developers.google.com/google-apps/calendar/v3/reference/events/insert

会話が終わればハングアウトURLは都度使い捨てという想定です。

こちらのサンプルコードは下記を参考にしてください。  
https://github.com/google/google-api-java-client-samples

## 処理の流れ

* ユーザーAがクライアントアプリを起動。
* ユーザーAがクライアントアプリ内でユーザーBへテレビ電話をする処理を呼び出す。
* ユーザーAのクライアントアプリはサーバーに対してハングアウトURLの生成と取得をリクエストする。
* サーバーはハングアウトURLを生成し、ユーザーAのクライアントにレスポンスを返す。
* サーバーはユーザーBのクライアントに対してPush通知でユーザーAからの呼び出しが来ていることと、ハングアウトのURLを通知する。
* ユーザーAは受け取ったハングアウトURLを用いてハングアウトアプリをIntent起動する。
* ユーザーBは受け取った通知からハングアウトURLを取得してハングアウトアプリをIntent起動する。
* ユーザーA, Bが同じハングアウトURLのルームに入り、会話を行う。
* 会話が終了すると、ユーザーA, Bともにもとのクライアントアプリに戻る。

[仕様書別添1_想定開発仕様案（コミュニケーション系）](http://www.town.namie.fukushima.jp/soshiki/1/10766.html) のページ4の図「Hangoutを用いた簡単電話アプリの通話イメージ」も参照してください。

## 課題・懸念点

* ハングアウトアプリの実装に依存してしまうと、ハングアウトアプリのアップデートにより大きな仕様変更があった場合に対応が必要になってしまう。
* 本レポジトリのサンプルコードをベースに試した結果では、通話終了後、正しくつながっぺアプリに戻れない事象が見られた。そのときのエラー処理については未検証。
* ハングアウトという別アプリを起動する以上、通話部分のUIは作ることができず、ハングアウトアプリ自体をそのまま用いるしかない。

# Skypeの利用の検討

Skype URI を使うと、Android 側からSkypeアプリを起動でき、call 先を指定してcallもできる。

https://msdn.microsoft.com/ja-jp/library/office/dn745884.aspx

（インストールされていない場合は、マーケットページへ飛ばす）

## 手順

### 呼び出し側アプリ
1. アプリを起動
2. 話したい相手を選ぶ
3. アプリから Skypeを起動して、相手にcall

### 呼び出され側
1. Skype で電話がかかってくる
2. 受け取ると、電話ができる

※事前（もしくは初回）にユーザー登録とログインをしてもらう必要がある

## 検証結果

テストした限りでは低帯域での音質は最も良かったが、Skypeへのログインを自動でさせることができず、利用を断念。

Skypeアカウント自体も、現状では浪江町タブレットのユーザーは未登録なので、ユーザー作成から行う必要がある。

浪江町タブレットについては、Googleアカウントへのログインとハングアウトアプリのプリインストールができているため、ハングアウトを用いるほうがユーザーがすぐにアプリを使えるので望ましいという判断になっている。


# WebRTCの利用の検討

APIを使って、自由に画面遷移を実装可能。他のアプリに依存しない。

ただし、サンプルコードを使って試してみた限りでは音質・画質が悪い。

一方、WebRTCは発展著しい分野でもあり、未検証部分が多い。

