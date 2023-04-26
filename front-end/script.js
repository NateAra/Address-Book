const addressList = document.getElementById('address-list');

// Function for display
function displayAddresses(){
    fetch('http://localhost:8080/addresses').then(response => response.json()).then(addresses => {
        addressList.innerHTML = '';
        addresses.forEach(address => {

            const li = document.createElement('li');
            
            li.innerHTML = `<strong>${address.name}</strong><br>
            ${address.city}<br>
            ${address.street}<br>
            <button onclick="deleteAddress(${address.id})">Delete</button>`;

            addressList.appendChild(li);
        });
    });
}


// Function to creating a new address
function createAddress(){
    
    const name = document.getElementById('name').value;
    const city = document.getElementById('city').value;
    const street = document.getElementById('street').value;

    const address = {
        name,
        city,
        street
    };

    fetch(`http://localhost:8080/addresses`, {
        method: 'POST',
        headers: {
            'Content-Type':'application/json'
        },
        body: JSON.stringify(address)
    }).then(() => {
        displayAddresses();
        document.getElementById('name').value = '';
        document.getElementById('city').value = '';
        document.getElementById('street').value = '';
    });
}

// Function for deleting
function deleteAddress(id){

    fetch(`http://localhost:8080/addresses/${id}`, {
        method: 'DELETE'
    }).then(() => {
        displayAddresses();
    });
}