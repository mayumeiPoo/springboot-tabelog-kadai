
    // フォームが送信された時の処理
function submitForm(event) {
    event.preventDefault(); // フォームのデフォルトの送信処理をキャンセル
    
    // フォームデータを取得
    const form = document.getElementById('shopForm');
    const formData = new FormData(form);

    // 緯度と経度をフォームデータに追加
    const latitude = document.getElementById('latform').value;
    const longitude = document.getElementById('lngform').value;
    formData.
    fo
append('latitude', latitude);
    formData.append('longitude', longitude);

    // データベースに送信
    fetch('/admin/shop/create', {
        method: 'POST',
        
    
body: formData
    })
    .then(response => {
        if (response.status === 200) {
            
 
console.log('データベースに保存されました');
            // 保存後の処理（例：リダイレクトなど）
        } else {
            console.error('データベースの保存に失敗しました');
        }
    })
    .catch(error => console.error('データベースに保存する際にエラーが発生しました:', error));
}

// フォームのボタンがクリックされた時に実行される関数


function getCoordinatesAndSubmit(event) {
    event.preventDefault(); // フォームのデフォルトの送信処理をキャンセル
    
    // 緯度と経度の取得
    getCoordinates()
    .then(() => {
        // フォームの送信処理を実行
        const form = document.getElementById('shopForm');
        form.
        
submit();
    })
    .catch(error => console.error('緯度と経度の取得に失敗しました:', error));
}

// 緯度と経度を取得する関数
function getCoordinates() {
    return new Promise((resolve, reject) => {
        const address = document.getElementById('addressInput').value;
        const apiKey = 'AIzaSyCtPm_1RFZHhvrBw95zXKoCHwbba26HJqw';
        const geocodingUrl = `https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(address)}&key=${apiKey}`;

        fetch(geocodingUrl)
            .then(response => response.json())
            .then(data => {
                if (data.status === 'OK') {
                    
        
const location = data.results[0].geometry.location;
                    const latitude = location.lat;
                    const longitude = location.lng;

                    

   
console.log(`Address: ${address}`);
                    
 
console.log(`Latitude: ${latitude}, Longitude: ${longitude}`);
                    
                    
           
setInputValue(latitude, longitude);
                    resolve();
                } else {
                    console.error(`Geocoding failed with status: ${data.status}`);
                    
                    
reject(new Error(`Geocoding failed with status: ${data.status}`));
                }
            })
            .catch(error => {
                console.error('Error fetching data:', error);
                
                reje
reject(error);
            });
    });
}


           

function setInputValue(latitude, longitude) {
    
 
var latFormElement = document.getElementById("latform");
    latFormElement.value = latitude;
    var lngFormElement = document.getElementById("lngform");
    lngFormElement.value = longitude;
}