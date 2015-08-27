# TunagappeProto
2015年度開発予定のコミュニケーションアプリ「つながっぺ」のプロトタイプです。下記で想定仕様を説明します。

# 通話部分にGoogleハングアウトを用いた想定仕様について

## クライアントサイド

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
