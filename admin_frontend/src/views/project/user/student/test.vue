<head>
  <meta charset="UTF-8">
  <title>Title</title>
  <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" />
</head>

<body>
<div id="app" v-cloak>
  <input type="file" @change="importExcel($event.target)" />
  <div style="overflow: auto;" v-if="tableTbody&&tableTbody.length>0">
    <table class="table table-bordered" style="min-width: 100%;">
      <thead>
      <tr>
        <th>#</th>
        <th v-for="(item,index) in tableHeader" :key="index">
          {{item}}
        </th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(row,index) in tableTbody" :key="index">
        <th scope="row">{{index}}</th>
        <td v-for="col in tableHeader">{{row[col]}}</td>
      </tr>
      </tbody>
    </table>
  </div>
</div>
</body>
<script src="https://cdn.bootcss.com/vue/2.4.2/vue.min.js"></script>
<script src="https://cdn.bootcss.com/xlsx/0.11.3/xlsx.full.min.js"></script>
<script>
  var app = new Vue({
    el: "#app",
    data() {
      return {
        wb: '',
        tableHeader: [],
        tableTbody: []
      }
    },
    methods: {
      importExcel(obj) {
        if (!obj.files) {
          return;
        }
        let file = obj.files[0],
          types = file.name.split('.')[1],
          fileType = ["xlsx", "xlc", "xlm", "xls", "xlt", "xlw", "csv"].some(item => item === types);
        if (!fileType) {
          alert("格式错误！请重新选择");
          return;
        }
        this.file2Xce(file).then(tabJson => {
          if (tabJson && tabJson.length > 0) {
            this.tableHeader = Object.keys(tabJson[0]);
            this.tableTbody = tabJson;
          }
        });
      },
      file2Xce(file) {
        return new Promise(function (resolve, reject) {
          let reader = new FileReader();
          reader.onload = function (e) {
            let data = e.target.result;
            this.wb = XLSX.read(data, {
              type: 'binary'
            });
            resolve(XLSX.utils.sheet_to_json(this.wb.Sheets[this.wb.SheetNames[0]]));
          };
          reader.readAsBinaryString(file);
        });
      }
    }
  })
</script>

<style scoped>

</style>
