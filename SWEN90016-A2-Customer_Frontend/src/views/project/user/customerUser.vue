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
      <el-form-item label="customer name" slot="search">
        <el-input v-model="searchForm.name" placeholder="name"></el-input>
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
          <el-table-column label="name" prop="name" align="center"></el-table-column>
          <el-table-column label="address" prop="address" align="center"></el-table-column>
          <el-table-column label="phone" prop="phone" align="center"></el-table-column>
          <el-table-column label="email" prop="email" align="center"></el-table-column>
          <el-table-column label="comment" prop="comment" align="center"></el-table-column>
          <el-table-column label="Biller" width="250" align="center">
            <template slot-scope="scope">
              <el-button type="primary" @click="showBiller(scope.row)">Biller</el-button>
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
        <el-form :model="biller" label-width="100px">
          <el-form-item label="Name">
            <el-input placeholder="Please input the service name" v-model="biller.name"></el-input>
          </el-form-item>
          <el-form-item label="Email">
            <el-input placeholder="Please input the cost" v-model="biller.email"></el-input>
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
  import { queryCustomer } from 'api/customerAPI'
  import { queryBiller } from 'api/billerAPI'
  import { emptyToNull } from '@/common/js/utils/form'
  import { dateFtt } from '@/common/js/utils'

  export default {
    name: 'traffic',
    mixins: [dataMixin],
    data () {
      return {
        searchForm: {
          name: '',
          pageIndex: 1
        },
        formData: {
          name: '',
          address: '',
          phone: '',
          email: '',
          comment: ''
        },
        biller: {
          name: '',
          email: ''
        }
      }
    },
    created () {
      this.getPageData()
    },
    methods: {
      async showBiller (data) {
          const res = await queryBiller(data.id)
          if (res.success) {
              this.biller = res.data.results[0]
          }
          this.dialogTitle = 'Biller Detail'
          this.setFormData(this.biller)
          this.toggleDialog()
      },
      formatDate (date) {
        return dateFtt('yyyy-MM-dd', new Date(date))
      },
      async getPageData () {
        const res = await queryCustomer(emptyToNull(this.searchForm))
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
