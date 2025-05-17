function showLoginPopup() {
        let uri = 'https://nid.naver.com/oauth2.0/authorize?' +
            'response_type=code' +
            '&client_id=PnXxGcAqLM5nVlmjE8zz' +
            '&state=NAVER_LOGIN_TEST' +
            '&redirect_uri=http://localhost:8080/naver/callback';

        window.location.href = uri;
    }