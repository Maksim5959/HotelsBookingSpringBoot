let foundRanges = document.querySelectorAll(".filter-item__range");

let rangeFunction = (range) => {
    let lower = range.querySelector(".lower");
    let upper = range.querySelector(".upper");
    let resultL = range.querySelector(".result-l");
    let resultU = range.querySelector(".result-u");

    let lowerVal = parseInt(lower.value);
    let upperVal = parseInt(upper.value);
    let lowMin = parseInt(lower.getAttribute("min"));
    let upMax = parseInt(upper.getAttribute("max"));

    lower.addEventListener("input", function () {
        lowerVal = parseInt(lower.value);
        upperVal = parseInt(upper.value);

        if (upperVal <= lowerVal) {
            upper.value = lowerVal;
            if (lowerVal === lowMin) {
                upper.value = lowerVal;
            }
        }

        lower.setAttribute("value", lowerVal);
        resultL.textContent = lowerVal;
        resultU.textContent = upperVal;
    });

    upper.addEventListener("input", function () {
        lowerVal = parseFloat(lower.value);
        upperVal = parseFloat(upper.value);

        if (lowerVal >= upperVal) {
            lower.value = upperVal;
            if (upperVal === upMax) {
                lower.value = upperVal;
            }
        }

        upper.setAttribute("value", upperVal);
        resultL.textContent = lowerVal;
        resultU.textContent = upperVal;
    });
};

for (let i = 0; i < foundRanges.length; i++) {
    foundRanges[i].addEventListener(
        "click",
        rangeFunction(foundRanges[i]),
        false
    );
    foundRanges[i].addEventListener(
        "mousemove",
        rangeFunction(foundRanges[i]),
        false
    );
    foundRanges[i].addEventListener(
        "change",
        rangeFunction(foundRanges[i]),
        false
    );
}

