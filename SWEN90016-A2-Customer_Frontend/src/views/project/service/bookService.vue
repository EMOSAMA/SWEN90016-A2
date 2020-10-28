<template>
  <div class="student">
    <assets-page
      :total="totalNumbers"
      :search-form="searchForm"
      :currentPage="searchForm.pageIndex"
      @current-change="handleCurrentChange"
      @data-add="showAdd"
      @data-cancel="handleCancel"
      @data-delete="handleDelete"
      @data-search="handleSearch">
      <el-form-item label="service name" slot="search">
        <el-input v-model="searchForm.serviceName" placeholder="name"></el-input>
      </el-form-item>
      <div slot="table">
        <el-table
          ref="multipleTable"
          :data="tableData"
          style="width: 100%"
          :border=true
          @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"></el-table-column>
          <el-table-column label="index" type="index" :index="indexMethod" width="55" align="center"></el-table-column>
          <el-table-column label="service name" prop="serviceName" align="center"></el-table-column>
          <el-table-column label="cost" prop="cost" align="center"></el-table-column>
          <el-table-column label="duration" prop="duration" align="center"></el-table-column>
          <el-table-column label="Book" width="250" align="center">
            <template slot-scope="scope">
              <el-button type="primary" @click="showEdit(scope.row)">book</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>type
    </assets-page>
    <div class="dialog-wrapper">
      <el-dialog
        :title="dialogTitle"
        :visible.sync="showDialog"
        :close-on-click-modal=false
        width="600px"
        @closed="dialogClosed">
        <el-form :model="application" label-width="100px">
          <el-form-item label="address">
            <el-input placeholder="address" v-model="application.location"></el-input>
          </el-form-item>
          <el-form-item label="Date">
            <el-date-picker
              v-model="application.date"
              type="date"
              placeholder="Choose Date"
              @change = "avaliableTime()"
              :picker-options="pickerOptions">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="Time">
            <el-select v-model="application.time" placeholder="time" no-data-text = "Please select date first">
              <el-option
                v-for="item in times"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                :disabled="item.disabled">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Message">
            <el-input placeholder="message" v-model="application.message"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button :loading="loading" type="primary" @click="save">Apply</el-button>
            <el-button :loading="loading" @click="toggleDialog">Cancel</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import { dataMixin } from '@/common/js/mixin'
  import routes from '@/common/js/config/routes'
  import AssetsPage from '@/layout/components/assets-page-sample'
  import { queryService } from 'api/serviceAPI'
  import { emptyToNull } from '@/common/js/utils/form'
  import { dateFtt } from '@/common/js/utils'
  import store from '@/store'
  import { getCustomer } from 'api/customerAPI'
  import { queryBiller } from 'api/billerAPI'
  import { addAppointment, queryAppointment } from 'api/appointmentAPI'

  export default {
    name: 'traffic',
    mixins: [dataMixin],
    data () {
      return {
        pickerOptions: {
          disabledDate (time) {
            return time.getTime() < Date.now() - 8.64e7
          }
        },
        loading: false,
        times: [],
        searchForm: {
          serviceName: '',
          pageIndex: 1
        },
        formData: {
          serviceName: '',
          cost: '',
          duration: ''
        },
        application: {
          customerId: store.getters.userId,
          serviceId: '',
          date: '',
          time: '',
          location: '',
          status: '',
          message: ''
        },
        userInfo: {
          id: store.getters.userId,
          name: '',
          email: '',
          password: '',
          address: '',
          phone: '',
          comment: ''
        },
        billerInfo: {
          billerId: '',
          name: '',
          email: ''
        }
      }
    },
    created () {
      this.getPageData()
      this.getUser()
      this.queryBiller()
    },
    methods: {
      async avaliableTime () {
        let nowHours = new Date().getHours()
        this.times = []
        this.application.time = ''
        let date = this.application.date
        const res = await queryAppointment({ date })
        if (res.success) {
          let applications = res.data.results
          let unavaliableTimes = new Set()
          let labels = [
            '9:00',
            '10:00',
            '11:00',
            '12:00',
            '13:00',
            '14:00',
            '15:00',
            '16:00'
          ]

          applications.forEach(item => {
            unavaliableTimes.add(parseInt(item.time))
          })
          for (var i = 1; i <= 8; i++) {
            if (!unavaliableTimes.has(i) && (i + 8 > nowHours || date.getFullYear() !== new Date().getFullYear() || date.getMonth() !== new Date().getMonth() || date.getDate() !== new Date().getDate())) {
              this.times.push({
                value: i,
                label: labels[i - 1]
              })
            } else {
              this.times.push({
                value: i,
                label: labels[i - 1],
                disabled: true
              })
            }
          }
          console.log(this.times)
        } else {
          this.avaliableTime()
        }
      },
      async getUser () {
        const res = await getCustomer(store.getters.userId)
        if (res.success) {
          this.userInfo = res.data
        } else {
          this.getUser()
        }
      },
      async queryBiller () {
        var customerId = store.getters.userId
        const res = await queryBiller({ customerId })
        if (res.success) {
          this.billerInfo = res.data.results[0]
        } else {
          this.getUser()
        }
      },
        async showEdit (data) {
          this.dialogTitle = 'Book Service'
          this.application.serviceId = data.id
          this.application.location = this.userInfo.address
          this.application.status = 'pending'
          this.setFormData(this.userInfo)
          this.toggleDialog()
        },
      async save () {
        this.loading = true
        if (!this._checkForm()) {
          return
        }
        let message = ''
        let res = null
        res = await addAppointment(this.application)
        if (res.success) {
          message = 'Apply Successful'
          this.$message.success(message)
          this.getPageData()
          this.toggleDialog()
        } else {
          message = 'Apply failed, please try again'
          this.$message.error(message)
        }
        this.times = []
        this.application = {
          customerId: store.getters.userId,
          serviceId: '',
          date: '',
          time: '',
          location: '',
          status: '',
          message: ''
        }
        this.loading = false
      },
      formatDate (date) {
        return dateFtt('yyyy-MM-dd', new Date(date))
      },
      async getPageData () {
        const res = await queryService(emptyToNull(this.searchForm))
        if (res.success) {
          this.tableData = res.data.results
          this.totalNumbers = res.data.totalNumbers
          console.log(this.tableData)
        } else {
          this.getPageData()
        }
      },
        _checkForm () {
            if (!this.application.location) {
                this.$message.error('Please input location')
                return false
            }
            if (!this.application.date) {
                this.$message.error('Please input date')
                return false
            }
            if (!this.application.time) {
                this.$message.error('Please input time')
                return false
            }
            return true
        }
    },
    components: {
      AssetsPage
    },
    computed: {
      routes () {
        return routes.student
      }
    }
  }
</script>

<style lang="scss" scoped>
  .student {
    padding: 20px;

    .student-icon {
      font-size: 28px;
    }
  }
</style>
