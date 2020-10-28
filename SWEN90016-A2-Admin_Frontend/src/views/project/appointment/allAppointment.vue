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
      <el-form-item label="customer name" slot="search">
        <el-input v-model="searchForm.customerName" placeholder="customer name"></el-input>
      </el-form-item>
      <el-form-item label="status" slot="search">
        <el-select v-model="searchForm.status" placeholder="status">
          <el-option
            v-for="item in options"
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
          <el-table-column type="selection" width="55" align="center"></el-table-column>
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
              {{times[scope.row.time-1]}}
            </template>
          </el-table-column>
          <el-table-column label="location" prop="location" align="center"></el-table-column>
          <el-table-column label="message" prop="message" align="center"></el-table-column>
          <el-table-column label="status" prop="status" align="center"></el-table-column>
          <el-table-column label="Operation" width="250" align="center">
            <template slot-scope="scope">
              <el-button type="primary" @click="showEdit(scope.row)">Edit</el-button>
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
          <el-form-item label="Status">
            <el-select v-model="formData.status" placeholder="status">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button :loading="loading" type="primary" @click="save">Save</el-button>
            <el-button @click="toggleDialog">Cancel</el-button>
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
  import { queryAppointment, updateAppointment } from 'api/appointmentAPI'
  import { emptyToNull } from '@/common/js/utils/form'
  import { dateFtt } from '@/common/js/utils'
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
        recordStatus: '',
        times: [
          '9:00',
          '10:00',
          '11:00',
          '12:00',
          '13:00',
          '14:00',
          '15:00',
          '16:00'
        ],
        options: [{
          value: 'pending',
          label: 'pending'
        }, {
          value: 'ongoing',
          label: 'ongoing'
        }, {
          value: 'finished',
          label: 'finished'
        }],
        searchForm: {
          serviceId: '',
          customerName: '',
          date: '',
          status: '',
          pageIndex: 1
        },
        formData: {
          id: '',
          serviceId: '',
          customerId: '',
          date: '',
          time: '',
          location: '',
          message: '',
          status: ''
        },
        loading: false,
        services: []
      }
    },
    created () {
      this.getPageData()
      this.queryAllServices()
    },
    methods: {
      async save () {
        this.loading = true
        if (this.recordStatus === this.formData.status) {
            this.$message.success('No change happen')
            this.toggleDialog()
            this.loading = false
            return;
        }
        let message = ''
        let res = null
        res = await updateAppointment(this.formData)
        message = 'Edit Successful'
        if (res.success) {
          this.$message.success(message)
          this.getPageData()
          this.queryAllServices()
          this.toggleDialog()
        }
        this.loading = false
      },
      showEdit (data) {
        this.dialogTitle = 'Edit Service'
        this.recordStatus = data.status
        this.setFormData(data)
        this.toggleDialog()
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
