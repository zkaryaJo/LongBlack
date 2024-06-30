(g => { var h, a, k, p = "The Google Maps JavaScript API", c = "google", l = "importLibrary", q = "__ib__", m = document, b = window; b = b[c] || (b[c] = {}); var d = b.maps || (b.maps = {}), r = new Set, e = new URLSearchParams, u = () => h || (h = new Promise(async (f, n) => { await (a = m.createElement("script")); e.set("libraries", [...r] + ""); for (k in g) e.set(k.replace(/[A-Z]/g, t => "_" + t[0].toLowerCase()), g[k]); e.set("callback", c + ".maps." + q); a.src = `https://maps.${c}apis.com/maps/api/js?` + e; d[q] = f; a.onerror = () => h = n(Error(p + " could not load.")); a.nonce = m.querySelector("script[nonce]")?.nonce || ""; m.head.append(a) })); d[l] ? console.warn(p + " only loads once. Ignoring:", g) : d[l] = (f, ...n) => r.add(f) && u().then(() => d[l](f, ...n)) })({
	key: "AIzaSyCGQoz2NgQAbugF5tlpvy_JK0w2MJKvQ9g",
	v: "weekly",
	// Use the 'v' parameter to indicate the version to use (weekly, beta, alpha, etc.).
	// Add other bootstrap parameters as needed, using camel case.
});

let map;
let center; // 내 위치

async function initMap() {
	const { Map } = await google.maps.importLibrary("maps");

	console.log(document.getElementById("map"));

	center = { lat: 37.4739605, lng: 126.8960606 };  //파라미터 <- 위/경도
	map = new Map(document.getElementById("map"), {
		center: center,
		zoom: 14,
		mapId: "4504f8b37365c3d0",
		// ...
	});
	findPlaces();
}

async function findPlaces() {
	const { Place } = await google.maps.importLibrary("places");
	//@ts-ignore
	const { AdvancedMarkerElement } = await google.maps.importLibrary("marker");
	const request = {
		textQuery: "카페",
		fields: ["displayName", "location", "businessStatus"],
		includedType: "cafe",
		isOpenNow: false,
		language: "ko-KR",
		maxResultCount: 7,
		minRating: 3.2,
		region: "kr",
		useStrictTypeFiltering: false,
	};
	//@ts-ignore
	const { places } = await Place.searchByText(request);

	if (places.length) {
		console.log(places);

		const { LatLngBounds } = await google.maps.importLibrary("core");
		const bounds = new LatLngBounds();

		// Loop through and get all the results.
		places.forEach((place) => {
			const markerView = new AdvancedMarkerElement({
				map,
				position: place.location,
				title: place.displayName,
			});

			bounds.extend(place.location);
			//console.log(place);
		});
		map.setCenter(bounds.getCenter());
	} else {
		console.log("No results");
	}
}

initMap();