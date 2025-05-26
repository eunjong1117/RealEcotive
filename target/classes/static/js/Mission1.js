const missions = {
    '상': ['플라스틱 1주일 안 쓰기', '대중교통만 이용하기', '3일간 쓰레기 안 버리기 챌린지'],
    '중': ['텀블러 사용하기', '일회용품 줄이기', '장바구니 사용하기'],
    '하': ['불 끄기', '양치컵 사용하기', '재활용 분리수거 하기']
  };

  let selectedDifficulty = null;
  let selectedMissions = []; // ✅ 사용자가 선택한 미션 목록

  function confirmDifficulty(level) {
    Swal.fire({
      title: `난이도 ${level} 등급을 선택하시겠습니까?`,
      text: "한 번 선택하면 다시 바꿀 수 없습니다.",
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#397362',
      cancelButtonColor: '#c8dbd5',
      confirmButtonText: '선택하기',
      cancelButtonText: '취소'
    }).then((result) => {
      if (result.isConfirmed) {
        selectedDifficulty = level;
        const randomMission = missions[level][Math.floor(Math.random() * missions[level].length)];
        document.getElementById('missionText').innerText = `[${level} 등급] ${randomMission}`;

        // ✅ 선택된 미션 저장
        selectedMissions.push(randomMission);

        // 버튼 비활성화
        const buttons = document.querySelectorAll('.difficulty-buttons button');
        buttons.forEach(btn => btn.disabled = true);
      }
    });
  }

  function toggleUploadForm() {
    if (!selectedDifficulty) {
      Swal.fire({
        icon: 'warning',
        title: '난이도 선택 필요!',
        text: '먼저 난이도를 선택해주세요!',
        confirmButtonColor: '#397362',
        background: '#cfe3d8',
        color: '#3c4d47',
      });
      return;
    }

    const form = document.getElementById('uploadForm');
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
  }

  document.getElementById('photoInput').addEventListener('change', function () {
    const file = this.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function (e) {
        const preview = document.getElementById('preview');
        preview.src = e.target.result;
        preview.style.display = 'block';
      };
      reader.readAsDataURL(file);
    }
  });

  document.getElementById('uploadForm').addEventListener('submit', function (e) {
    e.preventDefault();
    alert('미션 인증이 업로드 되었습니다.\n관리자의 승인을 기다려주세요!');
    this.reset();
    document.getElementById('preview').style.display = 'none';
  });

     // ✅ 미션 현황 버튼 클릭 이벤트
    document.getElementById('showMissionStatus').addEventListener('click', () => {
      // ✅ 난이도별 미션 목록 HTML 생성
        let html = '';
        for (const level of ['상', '중', '하']) {
          html += `<h3 style="color:#397362; text-align:center;">[난이도 ${level}]</h3><ul style="list-style:none; padding:0; text-align:center;">`;
          missions[level].forEach(m => {
            html += `<li style="margin: 6px 0;">${m}</li>`;
          });
          html += '</ul>';
        }

    Swal.fire({
        title: '미션 목록',
        html: `<div class="custom-swal-popup">${html}</div>`, // ✅ 높이 제한된 div로 감싸기
        confirmButtonColor: '#397362',
        width: '400px',
        background: '#cfe3d8',
        color: '#3c4d47',
        customClass: {
          popup: 'mission-status-popup'
        }
      });
  });