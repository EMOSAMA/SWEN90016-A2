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
        <el-select v-model="searchForm.serviceId" placeholder="service">
          <el-option
            v-for="item in services"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Date" slot="search">
        <el-date-picker
          v-model="searchForm.date"
          type="date"
          placeholder="Choose Date">
        </el-date-picker>
      </el-form-item>
      <div slot="table">
        <el-table
          ref="multipleTable"
          :data="tableData"
          style="width: 100%"
          :border=true
          @selection-change="handleSelectionChange">
          <el-table-column label="index" type="index" :index="indexMethod" width="55" align="center"></el-table-column>
          <el-table-column label="customer name" prop="customerName" align="center"></el-table-column>
          <el-table-column label="service name" prop="serviceName" align="center"></el-table-column>
          <el-table-column label="date" align="center">
            <template slot-scope="scope">
              {{formatDate(scope.row.date)}}
            </template>
          </el-table-column>
          <el-table-column label="time" align="center">
            <template slot-scope="scope">
              {{timesTranslate[scope.row.time - 1]}}
            </template>
          </el-table-column>
          <el-table-column label="location" prop="location" align="center"></el-table-column>
          <el-table-column label="message" prop="message" align="center"></el-table-column>
          <el-table-column label="status" prop="status" align="center"></el-table-column>
          <el-table-column label="Operation" width="250" align="center">
            <template slot-scope="scope">
              <el-button :loading="loading" type="primary" @click="cancelApplication(scope.row)">cancel</el-button>
              <el-button :loading="loading" type="primary" @click="showEdit(scope.row)">edit</el-button>
            </template>
          </el-table-column>
          <template slot="empty">
            <div class="noData">Empty</div>
          </template>
        </el-table>
      </div>
    </assets-page>
    <div class="dialog-wrapper">
      <el-dialog
        :title="dialogTitle"
        :visible.sync="showDialog"
        :close-on-click-modal=false
        width="600px"
        @closed="dialogClosed">
        <el-form :model="formData" label-width="100px">
          <el-form-item label="address">
            <el-input placeholder="address" v-model="formData.location"></el-input>
          </el-form-item>
          <el-form-item label="Date">
            <el-date-picker
              v-model="formData.date"
              type="date"
              placeholder="Choose Date"
              @change = "avaliableTime"
              :picker-options="pickerOptions">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="Time">
            <el-select v-model="formData.time" placeholder="time" no-data-text = "Please select date first">
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
            <el-input placeholder="message" v-model="formData.message"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button :loading="loading" type="primary" @click="save">Edit</el-button>
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
  import { updateAppointment, queryAppointment, deleteAppointment } from 'api/appointmentAPI'
  import { emptyToNull } from '@/common/js/utils/form'
  import { dateFtt } from '@/common/js/utils'
  import store from '@/store'
  import { queryAllServices } from 'api/serviceAPI'

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
        timesTranslate: [
          '9:00',
          '10:00',
          '11:00',
          '12:00',
          '13:00',
          '14:00',
          '15:00',
          '16:00'
        ],
        times: [],
        options: [{
          value: 'pending',
          label: 'pending'
        }, {
          value: 'ongoing',
          label: 'ongoing'
        }, {
          value: 'cancel',
          label: 'cancel'
        }, {
          value: 'finished',
          label: 'finished'
        }],
        searchForm: {
          serviceId: '',
          customerId: store.getters.userId,
          customerName: '',
          date: '',
          status: 'pending',
          pageIndex: 1
        },
        services: [],
          formData: {
              customerId: store.getters.userId,
              serviceId: '',
              date: '',
              time: '',
              location: '',
              status: '',
              message: ''
          },
          editingApplicationTime: '',
          editingApplicationDate: ''
      }
    },
    created () {
      this.getPageData()
      this.queryAllServices()
    },
    methods: {
        async save () {
            this.loading = true
            if (!this._checkForm()) {
                this.loading = false
                return
            }
            let message = ''
            let res = null
            res = await updateAppointment(this.formData)
            if (res.success) {
                message = 'Edit Successful'
                this.$message.success(message)
                this.getPageData()
                this.toggleDialog()
            } else {
                message = 'Edit failed, please try again'
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
        async showEdit (data) {
            this.loading = true
            this.dialogTitle = 'Edit Application'
            this.setFormData(data)
            this.editingApplicationTime = data.time
            this.editingApplicationDate = data.date
            await this.avaliableTime()
            this.toggleDialog()
            this.loading = false
        },
        async avaliableTime () {
            let nowHours = new Date().getHours()
            let date = this.formData.date
            if (new Date(date).toString() !== new Date((this.editingApplicationDate)).toString()) {
                this.formData.time = ''
            } else {
                this.formData.time = this.editingApplicationTime
            }
            this.times = []
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
                    if (!unavaliableTimes.has(i) && (i + 8 > nowHours || new Date(date).getFullYear() !== new Date().getFullYear() || new Date(date).getMonth() !== new Date().getMonth() || new Date(date).getDate() !== new Date().getDate())) {
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
                if (new Date(date).toString() === new Date((this.editingApplicationDate)).toString()) {
                    let index = this.editingApplicationTime - 1
                    this.times[index].disabled = false
                }
            } else {
                this.avaliableTime()
            }
        },
      async queryAllServices () {
        this.services = []
        const res = await queryAllServices()
        if (res.success) {
          res.data.forEach(service => {
            this.services.push({ value: service.id, label: service.serviceName })
          })
          console.log(res.data)
        } else {
          this.queryAllServices()
        }
      },
      async cancelApplication (data) {
        const res = await deleteAppointment([data.id])
        if (res.success) {
          this.$message.success('Cancel Successful')
          this.getPageData()
        } else {
          this.cancelApplication(data)
        }
      },
      formatDate (date) {
        return dateFtt('yyyy-MM-dd', new Date(date))
      },
      async getPageData () {
        const res = await queryAppointment(emptyToNull(this.searchForm))
        if (res.success) {
          this.tableData = res.data.results
          this.totalNumbers = res.data.totalNumbers
          console.log(this.tableData)
        } else {
          this.getPageData()
        }
      },
        _checkForm () {
            if (!this.formData.location) {
                this.$message.error('Please input location')
                return false
            }
            if (!this.formData.date) {
                this.$message.error('Please input date')
                return false
            }
            if (!this.formData.time) {
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
