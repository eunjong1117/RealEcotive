
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
