/*
 para botones de suma al carrito
 */

let btn = document.querySelector('.mainBtn');
let mbtn = document.querySelector('.minusBtn');
let pbtn = document.querySelector('.plusBtn');

btn.addEventListener("click", () => {
  if (btn.innerText == 'Comprar') {
      btn.innerText = 1;
      pbtn.style.display = 'inline-block';
      mbtn.style.display = 'inline-block';
    }
  }
)


mbtn.addEventListener("click", () => {
  if (btn.innerText == 5) {
    pbtn.style.display = 'inline-block';
  }
  if (btn.innerText < 2) {
    btn.innerText = 'Comprar';
    pbtn.style.display = 'none';
    mbtn.style.display = 'none';
  } else {
    btn.innerText = btn.innerText - 1;
  }
  }
)

pbtn.addEventListener("click", () => {
  btn.innerText = +(btn.innerText) + +1;

    if (btn.innerText == 5) {
      pbtn.style.display = 'none';
    }

  }
)

/*
 
 */