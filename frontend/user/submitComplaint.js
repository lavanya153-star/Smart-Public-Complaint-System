document.getElementById("form").addEventListener("submit", function(event){
    event.preventDefault();

    let name = document.getElementById("name").value;
    let email = document.getElementById("email").value;
    let location = document.getElementById("location").value;
    let details = document.getElementById("details").value;

    let msg = document.getElementById("msg");

    if(!name || !email || !location || !details){
        msg.innerHTML = "Please fill all fields!";
        msg.style.color = "red";
    } else {
        msg.innerHTML = "Complaint Submitted Successfully!";
        msg.style.color = "green";

        document.getElementById("form").reset();
    }
});
