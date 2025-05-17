function logoutNaver() {
        // 네이버 로그아웃 URL을 새 창이나 iframe으로 호출
        var iframe = document.createElement('iframe');
        iframe.style.display = 'none';
        iframe.src = 'https://nid.naver.com/nidlogin.logout';
        document.body.appendChild(iframe);

        // 1~2초 뒤 내 로그인 페이지로 이동
        setTimeout(function() {
            window.location.href = '/login';
        }, 2000);
    }
    window.onload = logoutNaver;