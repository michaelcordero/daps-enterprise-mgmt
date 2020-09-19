// window.addEventListener('beforeunload', function (event) {
//     let current_location = window.location.href
//     let weblogout = window.location.origin + '/weblogout'
//     let index = window.location.origin + '/'
//     let xhr = new XMLHttpRequest();
//     let target = event.composedPath()
//     xhr.open("GET", "/session", true);
//     // xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded')
//     xhr.send(null);
//     // if (xhr.status == 401) {
//         // window.location.replace(index)
//         // window.location.replace(index+window.location.hash)
//         // history.replaceState({},'', index)
//         if (current_location != index) {
//             // let vxhr = new XMLHttpRequest()
//             // vxhr.open("GET", "/", true);
//             // vxhr.setRequestHeader("Location","/");
//             // vxhr.setRequestHeader("Clear-Site-Data", "cache");
//             // vxhr.send();
//             // window.location.href = "/";
//             // window.location.reload()
//             // history.go(0)
//             event.stopImmediatePropagation()
//             // setTimeout(function (e) {
//             //     window.location.href = index
//             // },2000)
//             // window.stop()
//             // document.location = "/";
//             // return false;
//         }
//         // return false;
//     }
//     // xhr.onreadystatechange = function (ev) {
//     //     // if (current_location != index) {
//     //         if (xhr.status == 401) {
//     //             window.location.replace(index)
//     //         }
//     //     // }
//     // }
// })
