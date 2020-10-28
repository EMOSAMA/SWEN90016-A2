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
          <el-table-column label="Operation" width="250" align="center">
            <template slot-scope="scope">
              <el-button type="primary" @click="showEdit(scope.row)">Edit</el-button>
            </template>
          </el-table-column>
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
          <el-form-item label="Name">
            <el-input placeholder="Please input the service name" v-model="formData.serviceName"></el-input>
          </el-form-item>
          <el-form-item label="Cost">
            <el-input placeholder="Please input the cost" v-model="formData.cost"></el-input>
          </el-form-item>
          <el-form-item label="Duration">
            <el-input placeholder="Please input the duration" v-model="formData.duration"></el-input>
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
  import AssetsPage from '@/layout/components/assets-page'
  import { queryService, addService, deleteService, updateService } from 'api/serviceAPI'
  import { emptyToNull } from '@/common/js/utils/form'
  import { dateFtt } from '@/common/js/utils'

  export default {
    name: 'traffic',
    mixins: [dataMixin],
    data () {
      return {
        searchForm: {
          serviceName: '',
          pageIndex: 1
        },
        formData: {
          serviceName: '',
          cost: '',
          duration: ''
        },
        loading: false
      }
    },
    created () {
      this.getPageData()
    },
    methods: {
        async deletePageData () {
            const res = await deleteService(this.multipleSelection)
            if (res.success) {
                this.$message.success('Delete Successful')
                this.getPageData()
            } else {
                this.deletePageData()
            }
        },
        showAdd () {
            this.dialogTitle = 'Add New Service'
            this.toggleDialog()
        },
        showEdit (data) {
            this.dialogTitle = 'Edit Service'
            this.setFormData(data)
            this.toggleDialog()
        },
        async save () {
            this.loading = true
            if (!this._checkForm()) {
                return
            }
            let message = ''
            let res = null
            if (this.formData.id) {
                res = await updateService(this.formData)
                message = 'Edit Successful'
            } else {
                res = await addService(this.formData)
                message = 'Add Successful'
            }
            if (res.success) {
                this.loading = false
                this.$message.success(message)
                this.getPageData()
                this.toggleDialog()
            } else {
                this.loading = false
            }
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
            if (!this.formData.serviceName) {
                this.$message.error('Please input service name')
                return false
            }
            if (!this.formData.cost) {
                this.$message.error('Please input cost')
                return false
            }
            if (!this.formData.duration) {
                this.$message.error('Please input duration')
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
