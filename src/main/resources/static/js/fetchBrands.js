let brandContainer = document.getElementById("brandsContainer");

fetch('http://localhost:8080/fetch')
.then((response) => response.json())
    .then((json) => {
        for (const brand of json) {
            let brandHtml = '<div class="feature-card1-feature-card feature-card1-root-class-name1">\n'
            brandHtml += '<a href="http://localhost:8080/products/by-brand/' + brand.id + '">'
            brandHtml += '<img\n'
            brandHtml += 'alt="image"\n'
            brandHtml += 'src="' + brand.picUrl + '"\n'
            brandHtml += 'class="feature-card1-image"\n'
            brandHtml += '/>\n'
            brandHtml += '</a>\n'
            brandHtml += '</div>\n'

            brandContainer.innerHTML += brandHtml
        }
        }
    )

//<div class="feature-card1-feature-card feature-card1-root-class-name1">
//         <img
//                 alt="image"
//                 src="/images/985515.png"
//                 class="feature-card1-image"
//         />
//       </div>