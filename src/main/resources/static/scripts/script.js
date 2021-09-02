document.addEventListener('DOMContentLoaded', init);

const urlList = document.getElementById('urls');
const submitButton = document.getElementById('submit-btn');
const urlTextBox = document.getElementById('url');

function init() {
	submitButton.addEventListener('click', createUrl);
	
	fetch('/api/urls', {
		method: 'GET'
	}).then(response => {
		return response.json();
	}).then(json => {
		displayUrls(json.data);
	}).catch(err => {
		console.log(err)
	});
}

function createUrl(e) {
	e.preventDefault();
	const url = urlTextBox.value;
	const request = { url };
	
	fetch('/api/urls', {
		method: 'POST',
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(request)
	}).then(response => {
		return response.json();
	}).then(json => {
		if (json.status !== 'error') {
			document.location.href = "/";	
		}
		
		alert(json.message);
	}).catch(err => {
		console.log(err);
	});
}

function displayUrls(urls) {
	const header = `
		<tr>
			<th>Original URL</th>
			<th>Short URL</th>
		</tr>`;
	const urlDomElements = urls.map(url => {
		const { originalUrl, shortUrl } = url;
		const urlDom = `
			<tr>
				<td>
					${originalUrl}</a>
				</td>
				<td>
					<a href="/${shortUrl}">${shortUrl}</a>
				</td>
			</tr>`;
		
		return urlDom;
	});
	
	urlList.innerHTML = `<table border="1">${header}${urlDomElements.join("")}</table>`;
}
