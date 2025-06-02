
      function openMenu() {
        const menu = document.getElementById("menu");
        menu.style.right = "0px";
      }
      function closeMenu() {
        const menu = document.getElementById("menu");
        menu.style.right = "-250px";
      }
      window.addEventListener("resize", function () {
        const menu = document.getElementById("menu");
        if (window.innerWidth > 768) {
          menu.style.right = "0";
        } else {
          menu.style.right = "-250px";
        }
      });
      document.addEventListener("DOMContentLoaded", function () {
        const menu = document.getElementById("menu");
        if (window.innerWidth > 768) {
          menu.style.right = "0";
        } else {
          menu.style.right = "-250px";
        }
      });

      // ✅ 출석 완료 팝업 (페이지 진입 시 1회 보여주기)
        window.addEventListener('DOMContentLoaded', () => {
          if (!sessionStorage.getItem('attendanceShown')) {
            Swal.fire({
              title: '출석 완료!',
              text: '5 포인트가 지급되었습니다!',
              icon: 'success',
              confirmButtonText: 'OK',
              confirmButtonColor: '#397362',
              background: 'white',
              color: '#3c4d47'
            });
            sessionStorage.setItem('attendanceShown', 'true');
          }
        });


